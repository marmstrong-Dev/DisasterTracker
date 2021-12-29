package com.menus

import scala.io.StdIn
import com.data.Comment
import com.tools.Validator.check_for_null
import com.tools.Authenticator.loggedInStaff

object CommentMenu {
  def comment_menu_opts(): Unit = {
    println("\nWhat Would You Like To Do: \n 1.) Add Comment \n 2.) Delete Comment")
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

  def del_comments_opt(): Unit = {

  }
}
