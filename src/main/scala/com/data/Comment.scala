package com.data

import org.apache.spark.sql.Row
import java.sql.Timestamp
import java.time.LocalDateTime

// Class For Staff Generated Comments
class Comment(
               var commentTxt: String,
               var commentCreated: Timestamp,
               var commentEvent: String,
               var commentCreator: String
             ) {
  def this() = {this("", Timestamp.valueOf(LocalDateTime.now()), "", "")}

  // Add Individual Comment
  def add_comment(): Unit = {
    val addCommentQuery =
      s"""
      INSERT INTO Comments
      VALUES('${this.commentTxt}', '${this.commentCreated}', '${this.commentEvent}', '${this.commentCreator}')
      """

    DbCon.spark_add_one(addCommentQuery)
  }

  // Lookup And Return List Of Comments For A Staff Member
  def lookup_comments(): Array[Row] = {
    val lookupCommentQuery = s"SELECT * FROM Comments WHERE comment_creator = '${this.commentCreator}'"

    val commentList = DbCon.spark_lookup_many(lookupCommentQuery)
    var counter = 0

    for(i <- 0 until commentList.length) {
      counter += 1
      println(counter + ":  |  " + commentList(i)(0) + "  |  " + commentList(i)(1))
    }

    return commentList
  }

  // Delete Individual Comment
  def del_comment(): Unit = {
    val delCommentQuery =
      s"""
      SELECT * FROM Comments
      WHERE comment_created != '${this.commentCreated}'
      """

    DbCon.spark_delete_one(delCommentQuery, false)
  }
}
