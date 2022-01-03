package com.menus

import scala.io.{AnsiColor, StdIn}
import com.data.{Comment, DbCon}
import com.tools.Validator.check_for_null
import com.tools.Authenticator.loggedInStaff
import java.sql.Timestamp

object CommentMenu {
  // Main Sub Router For Comments Menu
  def comment_menu_opts(): Unit = {
    println(s"\n${AnsiColor.GREEN}${AnsiColor.BOLD}Comment Menu${AnsiColor.RESET}")
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

  // Add Individual Comment
  def add_comments_opt(): Unit = {
    val candidateComment = new Comment()

    println("Which Event Is This For?")
    candidateComment.commentEvent = StdIn.readLine()
    println("Enter Comment")
    candidateComment.commentTxt = StdIn.readLine()

    if(check_for_null(Array[String](candidateComment.commentTxt, candidateComment.commentEvent))) {
      candidateComment.commentCreator = loggedInStaff.staffEmailAddress
      candidateComment.add_comment()
    }
  }

  // Delete Individual Comment
  def del_comments_opt(): Unit = {
    val candidateComment = new Comment()
    candidateComment.commentCreator = loggedInStaff.staffEmailAddress
    val commentsList = candidateComment.lookup_comments()

    println("\nWhich Comment Would You Like To Delete?")
    val commentSelector = StdIn.readLine()
    println("Are You Sure You Want To Delete (Y to Confirm)?")
    val confirm = StdIn.readLine()

    if(confirm.toUpperCase() == "Y") {
      try {
        candidateComment.commentCreated = commentsList(commentSelector.toInt - 1)(1).asInstanceOf[Timestamp]
        candidateComment.del_comment()
      }
      catch {
        case e: Exception => println("Invalid Selection")
      }
    }
    else {
      println("Invalid Option")
    }
  }
}
