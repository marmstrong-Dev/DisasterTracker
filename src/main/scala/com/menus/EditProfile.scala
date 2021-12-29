package com.menus

import scala.io.StdIn
import com.tools.Authenticator.loggedInStaff

object EditProfile {
  def profile_opts(): Unit = {
    println("\nWhat Would You Like To Do: \n 1.) Edit Profile \n 2.) Delete Profile")
    if(loggedInStaff.isAdmin) {println(" 3.) Make Admin")}

    val selector = StdIn.readLine()

    if(selector == "1") {
      profile_change()
    }
    else if(selector == "2") {
      delete_profile()
    }
    else if(selector == "3" && loggedInStaff.isAdmin) {
      new_admin()
    }
    else {
      println("Invalid Option")
    }
  }

  def profile_change(): Unit = {

  }

  def new_admin(): Unit = {
    loggedInStaff.find_all_staff()

    println("Which Staff Would You Like To Make Admin?")
    val email = StdIn.readLine()

    loggedInStaff.make_admin(email)
  }

  def delete_profile(): Unit = {
    if(loggedInStaff.isAdmin) {
      loggedInStaff.find_all_staff()

      println("Which Staff Would You Like To Delete?")
      val delEmail = StdIn.readLine()

      loggedInStaff.del_staff(delEmail)
    }
    else {
      println("Are You Sure You Want To Delete Your Account \n(Enter Y To Confirm)?")
      val confirm = StdIn.readLine()

      if(confirm.toUpperCase() == "Y") {
        loggedInStaff.del_staff(loggedInStaff.staffEmailAddress)
      }
    }
  }
}
