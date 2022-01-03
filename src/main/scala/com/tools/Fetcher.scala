package com.tools

import com.data.DbCon.con
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.sql.types.{StringType, StructType}
import ujson.{Arr, Value}
import scala.collection.mutable.ArrayBuffer

object Fetcher {
  def fetch_data(dataSource: String): DataFrame = {
    val url = dataSource
    val result = scala.io.Source.fromURL(url).mkString

    con.sql("USE ProjectOne")

    //val dataDe = spark.sparkContext.makeRDD(result :: Nil).toDS()
    //val dat = spark.read.json("C:\\Users\\narma\\Documents\\Proj17\\dev\\test.json")
    //val rd = con.read.json(result).collect()
    //rd.foreach(x => println(x))
    //arrayJson.value.foreach(x => println(x("title")))

    val jsonPure: Value = ujson.read(result)
    val arrayJson: Arr = jsonPure("events").arr

    //val cols = Seq("id", "title", "link", "category")
    //val colData = con.sparkContext.parallelize(cols)

    /*
    val colStructure = Seq(
      for(i <- 0 until arrayJson.arr.length) {
        Row(arrayJson.value(i)("id"), arrayJson.value(i)("title"), arrayJson.value(i)("link"))
      }
    )*/

    val colData = new ArrayBuffer[Row]()

    for(i <- 0 until arrayJson.arr.length) {
      colData += Row(
        (i + 1).toString,
        arrayJson.value(i)("id").toString(),
        arrayJson.value(i)("title").toString(),
        arrayJson.value(i)("link").toString())
    }

    //val completedData = colData.toArray
    val colStructure = colData.toSeq

    val colSchema = new StructType()
      .add("event_num", StringType)
      .add("event_id", StringType)
      .add("event_title", StringType)
      .add("source_link", StringType)

    val colDf: DataFrame = con.createDataFrame(
      con.sparkContext.parallelize(colStructure),
      colSchema
    )

    colDf.show(false)
    return colDf
    //arrayJson. (x => println(x))
  }

  // Return All Categories
  def fetch_cats(catSource: String): Unit = {
    val url = catSource
    val result = scala.io.Source.fromURL(url).mkString

    con.sql("USE ProjectOne")

    val jsonPure: Value = ujson.read(result)
    val arrayJson: Arr = jsonPure("categories").arr

    val colData = new ArrayBuffer[Row]()

    for(i <- 0 until arrayJson.arr.length) {
      colData += Row(arrayJson.value(i)("id").toString(), arrayJson.value(i)("title").toString(), arrayJson.value(i)("link").toString())
    }

    val colStructure = colData.toSeq

    val colSchema = new StructType()
      .add("cat_id", StringType)
      .add("cat_title", StringType)
      .add("cat_link", StringType)

    val colDf: DataFrame = con.createDataFrame(
      con.sparkContext.parallelize(colStructure),
      colSchema
    )

    colDf.show(false)
  }

  // Return Single Category
  def fetch_single_cat(catSource: String, catId: Int): Unit = {
    val url = catSource
    val result = scala.io.Source.fromURL(url).mkString

    con.sql("USE ProjectOne")

    val jsonPure: Value = ujson.read(result)
    val colData = Row(catId, jsonPure("title"), jsonPure("description"), jsonPure("link"))
    val colStructure = colData.toSeq

    println("\nID: " + colData(0))
    println("Title: " + colData(1))
    println("Link: " + colData(3))
    println("Description: \n" + colData(2) + "\n")
  }

  def fetch_single_event(eventSource: String, eventId: Int): Unit = {
    val url = eventSource
    val result = scala.io.Source.fromURL(url).mkString

    con.sql("USE ProjectOne")

    val jsonPure: Value = ujson.read(result)
    val colData = Row(
      jsonPure("events")(eventId)("id"),
      jsonPure("events")(eventId)("title"),
      jsonPure("events")(eventId)("link"),
      jsonPure("events")(eventId)("categories")(0)("title"))
    val colStructure = colData.toSeq

    println("ID: " + colData(0))
    println("Title: " + colData(1))
    println("Link: " + colData(2))
    println("Category: " + colData(3) + "\n")
  }
}
