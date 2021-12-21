package com.data

class Staff (
              var staffId: Int,
              var staffFirstName: String,
              var staffLastName: String,
              var staffEmailAddress: String,
              var staffPassword: String,
              var isAdmin: Boolean
            ) {
  def this() = {this(0, "", "", "", "", false)}

  def this(staffEmail: String, staffPass: String) = {
    this(0, "", "", staffEmail, staffPass, false)
  }
}
