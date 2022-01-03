package com.menus

import com.tools.Fetcher.{fetch_cats, fetch_single_cat}

import scala.io.{AnsiColor, StdIn}

object CategoryMenu {
  // Main Option Menu For Categories
  def category_opts(): Unit = {
    println(s"\n${AnsiColor.GREEN}${AnsiColor.BOLD}Category Menu${AnsiColor.RESET}")
    println("What Would You Like To Do?\n 1.)Print All Categories\n 2.)Lookup Category")
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

  // Print List Of Categories
  def print_all_cats(): Unit = {
    println(s"\n${AnsiColor.GREEN}${AnsiColor.BOLD}Category List${AnsiColor.RESET}")
    fetch_cats("https://eonet.gsfc.nasa.gov/api/v2.1/categories")
  }

  // Print Details For Individual Category
  def see_cat_details(): Unit = {
    fetch_cats("https://eonet.gsfc.nasa.gov/api/v2.1/categories")
    println("Which Category Would You Like To See (Enter ID)?")
    val catSelector = StdIn.readLine()

    fetch_single_cat(s"https://eonet.gsfc.nasa.gov/api/v2.1/categories/${catSelector}", catSelector.toInt)
  }
}
