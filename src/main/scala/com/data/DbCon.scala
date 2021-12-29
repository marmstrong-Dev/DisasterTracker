package com.data

import org.apache.spark.sql.SparkSession
import scala.collection.mutable.ArrayBuffer
import org.apache.log4j.Logger
import org.apache.log4j.Level

object DbCon {
  var con: SparkSession = null

  val createStaffTable = """
  CREATE TABLE IF NOT EXISTS Staff (
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

  def spark_add_one(query: String): Unit = {
    con.sparkContext.setLogLevel("ERROR")
    println("Adding Entity")

    con.sql("USE ProjectOne")
    con.sql(query).show()
  }

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

  def spark_update_one(updateQuery: String): Unit = {

  }

  def close_spark(): Unit = {
    con.close()
  }
}
