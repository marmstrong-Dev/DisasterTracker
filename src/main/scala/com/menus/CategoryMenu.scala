package com.menus

import com.tools.Fetcher.fetch_cats
import scala.io.StdIn

object CategoryMenu {
  def category_opts(): Unit = {
    println("\nWhat Would You Like To Do\n 1.)Print All Categories\n 2.)Lookup Category")
    val catOptSelector = StdIn.readLine()

    if(catOptSelector == "1") {
      print_all_cats()
    }
    else if(catOptSelector == "2") {
      see_cat_details()
    }
    else {
      println("Invalid Selection")
    }
  }

  def print_all_cats(): Unit = {
    fetch_cats("https://eonet.gsfc.nasa.gov/api/v2.1/categories")
  }

  def see_cat_details(): Unit = {

  }
}
