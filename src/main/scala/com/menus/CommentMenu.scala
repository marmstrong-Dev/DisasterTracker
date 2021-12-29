package com.menus

import scala.io.StdIn

object CommentMenu {
  def comment_menu_opts(): Unit = {
    println("What Would You Like To Do: \n 1.) Add Comment \n 2.) Delete Comment")
    val selector = StdIn.readLine()

    if(selector == "1") {
      add_comments_opt()
    }
    else if(selector == "2") {
      del_comments_opt()
    }
    else {
      println("Invalid Selection")
    }
  }

  def add_comments_opt(): Unit = {

  }

  def del_comments_opt(): Unit = {

  }
}
