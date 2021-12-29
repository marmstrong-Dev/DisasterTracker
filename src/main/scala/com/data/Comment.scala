package com.data

import java.time.LocalDateTime

class Comment(
             var commentTxt: String,
             var commentCreated: LocalDateTime,
             var commentEvent: String,
             var commentCreator: String
             ) {
  def this() = {this("", LocalDateTime.now(), "", "")}

  def add_comment(): Unit = {
    val addCommentQuery =
      s"""
      INSERT INTO Comments
      VALUES('${this.commentTxt}', '${this.commentCreated}', '${this.commentEvent}', '${this.commentCreator}')
      """

    DbCon.spark_add_one(addCommentQuery)
  }

  def del_comment(): Unit = {

  }
}
