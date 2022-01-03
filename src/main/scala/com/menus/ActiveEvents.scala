package com.menus

import scala.io.{AnsiColor, StdIn}
import com.data.DbCon.spark_lookup_many
import com.menus.CategoryMenu.print_all_cats
import com.tools.Fetcher._

object ActiveEvents {
  // Sub Router For Events
  def print_event_menu(): Unit = {
    println(s"\n${AnsiColor.GREEN}${AnsiColor.BOLD}Event Menu${AnsiColor.RESET}")
    println("What Would You Like To See: \n 1.) View All Events\n 2.) View By Category\n 3.) Lookup Event")
    val selector = StdIn.readLine()

    if(selector == "1") {
      print_all()
    }
    else if(selector == "2") {
      print_by_cat()
    }
    else if(selector == "3") {
      print_event()
    }
    else {
      println("Invalid Selection")
    }
  }

  // Print All Open Events
  def print_all(): Unit = {
    println(s"\n${AnsiColor.GREEN}${AnsiColor.BOLD}All Events List${AnsiColor.RESET}")
    val url = "https://eonet.gsfc.nasa.gov/api/v2.1/events?status=open"
    fetch_data(url)
  }

  // Print Events By Category
  def print_by_cat(): Unit = {
    print_all_cats()
    println("Select A Category (ID):")
    val catSelector = StdIn.readLine()

    try {
      val url = s"https://eonet.gsfc.nasa.gov/api/v2.1/categories/${catSelector.toInt}?status=open"
      fetch_data(url)
    }
    catch {
      case e: Exception => println("Invalid Selection")
    }
  }

  // Lookup Individual Event
  def print_event(): Unit = {
    val eventList = fetch_data("https://eonet.gsfc.nasa.gov/api/v2.1/events?status=open").collect()
    println("Select An Event:")
    val eventSelector = StdIn.readLine()

    var url = "https://eonet.gsfc.nasa.gov/api/v2.1/events?status=open"
    fetch_single_event(url, eventSelector.toInt - 1)

    var remQuote = eventList(eventSelector.toInt - 1)(1).toString
    remQuote = remQuote.substring(1, remQuote.length - 1)

    val commentList = spark_lookup_many(s"SELECT * FROM Comments WHERE comment_event = '${remQuote}'")
    for(i <- 0 until commentList.length) {
      println("Creator: " + commentList(i)(3))
      println("Created On: " + commentList(i)(1))
      println(commentList(i)(0) + "\n")
    }
  }
}
