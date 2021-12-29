package com.data

import java.time.LocalDateTime

class Comment(
             var commentId: Int,
             var commentTxt: String,
             var commentCreated: LocalDateTime,
             var commentEvent: Int,
             var commentCreator: Int
             ) {
  def this() = {this(0, "", LocalDateTime.now(), 0, 0)}

  def add_comment(): Unit = {
    val addCommentQuery =
      s"""
      INSERT INTO Comments(comment_txt, comment_created, comment_event, comment_creator)
      VALUES('${this.commentTxt}', '${this.commentCreated}', ${this.commentEvent}, ${this.commentCreator})
      """
  }

  def del_comment(): Unit = {

  }
}
