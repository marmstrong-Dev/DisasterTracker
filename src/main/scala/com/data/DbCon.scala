package com.data

import org.apache.spark.sql.{SaveMode, SparkSession, functions}
import com.password4j._
import scala.collection.mutable.ArrayBuffer
import org.apache.log4j.Logger
import org.apache.log4j.Level

object DbCon {
  var con: SparkSession = null

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

  val createCommentsTable = """
  CREATE TABLE IF NOT EXISTS Comments (
    comment_txt STRING,
    comment_created TIMESTAMP,
    comment_event STRING,
    comment_creator STRING
  )
  COMMENT 'Event Comments Table'
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

    spark.close()
  }

  // Add Individual Entity
  def spark_add_one(query: String): Unit = {
    con.sparkContext.setLogLevel("ERROR")
    println("Adding Entity")

    con.sql("USE ProjectOne")
    con.sql(query).show()
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
  def spark_lookup_many(allQuery: String): Unit = {
    println("Looking Up All Entities")

    con.sql("USE ProjectOne")
    con.sql(allQuery).show(false)
  }

  // Delete Individual Record
  def spark_delete_one(deleteEmail: String): Unit = {
    println("Deleting Entity")

    con.sql("USE ProjectOne")
    var newDf = con.sql(s"SELECT * FROM Staff WHERE staff_email_address != '${deleteEmail}'")

    newDf = newDf.withColumn("is_supervisor", functions.when(
      functions.col("staff_email_address").equalTo(deleteEmail), 1)
      .otherwise(functions.col("is_supervisor")))

    newDf.write.partitionBy("is_supervisor").mode("Overwrite").saveAsTable("Staff2")

    con.sql("DROP TABLE IF EXISTS Staff")
    con.sql("ALTER TABLE Staff2 RENAME TO Staff")
    con.sql("SELECT * FROM Staff").show()
  }

  // Update Single Entity
  def spark_update_one(updateEmail: String): Unit = {
    println("Modifying Entity")

    con.sql("USE ProjectOne")
    var newDf = con.sql("SELECT * FROM Staff")

    newDf = newDf.withColumn("is_supervisor", functions.when(
      functions.col("staff_email_address").equalTo(updateEmail), 1)
      .otherwise(functions.col("is_supervisor")))

    //newDf.createOrReplaceGlobalTempView("TempData")
    //newDf.createOrReplaceTempView("TempData")
    //con.sql(s"INSERT INTO Staff PARTITION (is_supervisor = 1) VALUES ('Global','Admin', 'g.admin@address.com', '${Password.hash("PassAdmin").withBCrypt().getResult}')")
    //con.sql("DROP TABLE IF EXISTS StaffNew")
    //con.sql(createStaffTable)
    //con.sql("UPDATE Staff SET is_supervisor = 1 WHERE staff_email_address = 'marms@address.com'")
    //con.sql("SHOW PARTITIONS Staff").show()
    //con.sql("TRUNCATE TABLE Staff PARTITION(is_supervisor = 1)")
    //con.sql("ALTER TABLE Staff PARTITION(is_supervisor INT)")
    //con.sql("INSERT INTO Staff2 PARTITION(is_supervisor = 1) SELECT * FROM StaffNew")

    newDf.write.partitionBy("is_supervisor").mode("Overwrite").saveAsTable("Staff2")

    con.sql("DROP TABLE IF EXISTS Staff")
    con.sql("ALTER TABLE Staff2 RENAME TO Staff")
    con.sql("SELECT * FROM Staff").show()
  }

  // Close Spark Session
  def close_spark(): Unit = {
    con.close()
  }
}
