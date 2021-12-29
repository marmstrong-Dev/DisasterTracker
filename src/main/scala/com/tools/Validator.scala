package com.tools

import com.data.Staff

object Validator {
  var errorMsg = ""

  // Checks List Of Values For Empty Strings Or Null Values
  def check_for_null(checkList: Array[String]): Boolean = {
    var isValid = true

    for(i <- 0 until checkList.length) {
      if(checkList(i) == "" || checkList(i) == null) {
        isValid = false
      }
    }

    if(!isValid) {
      errorMsg = "Please Fill In All Fields"
    }

    return isValid
  }

  // Checks If Email Address Is Valid
  def check_email(email: String): Boolean = {
    var isValid = false

    if(email.contains("@")) {
      isValid = true

      val lookupEmail = new Staff(email, "")
      lookupEmail.lookup_staff()

      if(lookupEmail.staffFirstName != "") {
        isValid = false
        errorMsg = "Email Already Taken"
      }
    }
    else {
      errorMsg = "Invalid Email Address"
    }

    return isValid
  }

  // Checks Password Length And Comparison
  def check_password(s1: String, s2: String): Boolean = {
    var isValid = false

    if(s1 == s2) {
      if(s1.length >= 8) {
        isValid = true
      }
      else {
        errorMsg = "Password Must Be At Least 8 Characters"
      }
    }
    else {
      errorMsg = "Passwords Do Not Match"
    }

    return isValid
  }
}
