package com.data

import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.apache.log4j.Level

object DbCon {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  def spark_opener(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Disaster Tracker")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    println("Created Spark Session")

    spark.close()
  }
}
