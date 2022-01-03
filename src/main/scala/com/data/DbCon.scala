package com.data

import org.apache.spark.sql.{SaveMode, SparkSession, functions, Row}
import com.password4j._
import scala.collection.mutable.ArrayBuffer
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.DataFrame

object DbCon {
  var con: SparkSession = null

  // Initialize Staff Table
  val createStaffTable = """
  CREATE TABLE IF NOT EXISTS Staff2 (
    staff_first_name VARCHAR(125),
    staff_last_name VARCHAR(125),
    staff_email_address STRING,
    staff_password STRING
  )
  COMMENT 'Staff Account Data'
  PARTITIONED BY (is_supervisor INT)
  """

  // Initialize Comments Table
  val createCommentsTable = """
  CREATE TABLE IF NOT EXISTS Comments (
    comment_txt STRING,
    comment_created TIMESTAMP,
    comment_event STRING,
    comment_creator STRING
  )
  COMMENT 'Event Comments Table'
  CLUSTERED BY (comment_event) SORTED BY (comment_creator) INTO 32 BUCKETS
  """

  // Open Spark Session
  def open_spark(): SparkSession = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .appName("Disaster Tracker")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    return spark
  }

  // Init Tables
  def spark_opener(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Disaster Tracker")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    println("Created Spark Session\n")

    spark.sql("CREATE DATABASE IF NOT EXISTS ProjectOne")
    spark.sql("USE ProjectOne")
    spark.sql(createStaffTable)
    spark.sql(createCommentsTable)
    con.sql(s"INSERT INTO Staff PARTITION (is_supervisor = 1) VALUES ('Global','Admin', 'g.admin@address.com', '${Password.hash("PassAdmin").withBCrypt().getResult}')")
    spark.close()
  }

  // Add Individual Entity
  def spark_add_one(query: String): Unit = {
    con.sparkContext.setLogLevel("ERROR")
    println("Adding Entity")

    con.sql("USE ProjectOne")
    con.sql(query)
  }

  // Lookup Individual Entity
  def spark_lookup_one(query: String): Array[String] = {
    val qData = new ArrayBuffer[String]()
    println("Looking Up Entity")

    con.sql("USE ProjectOne")
    val lookupData = con.sql(query).collect()
    lookupData.foreach(x => for(i <- 0 until(x.size)){
      qData += x.get(i).toString
    })

    val res = qData.toArray
    return res
  }

  // Fetch All Data For Table
  def spark_lookup_many(allQuery: String): Array[Row] = {
    println("\nLooking Up Entities")

    con.sql("USE ProjectOne")

    val returnArray = con.sql(allQuery)
    val resArray = returnArray.collect()

    return resArray
  }

  // Delete Individual Record
  def spark_delete_one(deleteQuery: String, isStaff: Boolean): Unit = {
    println("Deleting Entity")

    con.sql("USE ProjectOne")
    val newDf = con.sql(deleteQuery)

    if(isStaff) {
      del_one_staff(newDf)
    }
    else {
      del_one_comment(newDf)
    }
  }

  // Delete One Staff Member
  def del_one_staff(newDf: DataFrame): Unit = {
    newDf.write.partitionBy("is_supervisor").mode("Overwrite").saveAsTable("Staff2")

    con.sql("DROP TABLE IF EXISTS Staff")
    con.sql("ALTER TABLE Staff2 RENAME TO Staff")
  }

  // Delete One Comment
  def del_one_comment(newDf: DataFrame): Unit = {
    newDf.write.bucketBy(32, "comment_creator").mode("Overwrite").saveAsTable("Comments2")

    con.sql("DROP TABLE IF EXISTS Comments")
    con.sql("ALTER TABLE Comments2 RENAME TO Comments")
  }

  // Update Individual Entity
  def spark_update_one(changeCandidate: Staff, adminChange: Boolean): Unit = {
    println("Modifying Entity")

    con.sql("USE ProjectOne")
    var newDf = con.sql("SELECT * FROM Staff")

    if(adminChange) {
      newDf = newDf.withColumn("is_supervisor", functions.when(
        functions.col("staff_email_address").equalTo(changeCandidate.staffEmailAddress), 1)
        .otherwise(functions.col("is_supervisor")))
    }
    else {
      newDf = newDf.withColumn("staff_first_name", functions.when(
        functions.col("staff_email_address").equalTo(changeCandidate.staffEmailAddress), value = changeCandidate.staffFirstName)
        .otherwise(functions.col("staff_first_name")))

      newDf = newDf.withColumn("staff_last_name", functions.when(
        functions.col("staff_email_address").equalTo(changeCandidate.staffEmailAddress), value = changeCandidate.staffLastName)
        .otherwise(functions.col("staff_last_name")))

      newDf = newDf.withColumn("staff_password", functions.when(
        functions.col("staff_email_address").equalTo(changeCandidate.staffEmailAddress), value = changeCandidate.staffPassword)
        .otherwise(functions.col("staff_password")))
    }

    newDf.write.partitionBy("is_supervisor").mode("Overwrite").saveAsTable("Staff2")

    con.sql("DROP TABLE IF EXISTS Staff")
    con.sql("ALTER TABLE Staff2 RENAME TO Staff")
  }

  // Close Spark Session
  def close_spark(): Unit = {
    con.close()
  }
}
