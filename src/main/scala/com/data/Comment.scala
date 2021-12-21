package com.data

class Comment(
             var commentId: Int,
             var commentTxt: String,
             var commentCreated: Any,
             var commentEvent: Int,
             var commentCreator: Int
             ) {
  def this() = {this(0, "", "", 0, 0)}
}
