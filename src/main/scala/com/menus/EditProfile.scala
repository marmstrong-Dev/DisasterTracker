package com.menus

import scala.io.{AnsiColor, StdIn}
import com.tools.Authenticator.loggedInStaff
import com.tools.Validator.{check_for_null, check_password}
import com.data.Staff
import com.password4j.Password

object EditProfile {
  // Sub Router For Profile Options
  def profile_opts(): Unit = {
    println(s"\n${AnsiColor.GREEN}${AnsiColor.BOLD}Profile Menu${AnsiColor.RESET}")
    println("What Would You Like To Do: \n 1.) Edit Profile \n 2.) Delete Profile")
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

  // Modify Staff Profile
  def profile_change(): Unit = {
    val changeList = new Array[String](4)

    println("Enter First Name")
    changeList(0) = StdIn.readLine()
    println("Enter Last Name")
    changeList(1) = StdIn.readLine()

    println("Enter New Password")
    changeList(2) = StdIn.readLine()
    println("Confirm New Password")
    changeList(3) = StdIn.readLine()

    if(check_for_null(changeList) && check_password(changeList(2), changeList(3))) {
      val changeStaff = new Staff(loggedInStaff.staffEmailAddress, Password.hash(changeList(2)).withBCrypt().getResult)

      changeStaff.staffFirstName = changeList(0)
      changeStaff.staffLastName = changeList(1)

      changeStaff.edit_staff()
    }
  }

  // Make Staff Member Supervisor
  def new_admin(): Unit = {
    loggedInStaff.find_all_staff()

    println("\nWhich Staff Would You Like To Make Admin?")
    val email = StdIn.readLine()
    val newAdmin = new Staff(email, "")

    newAdmin.make_admin()
    println("Admin Rights Granted")
  }

  // Delete Profile - Can Delete Others If Supervisor
  def delete_profile(): Unit = {
    if(loggedInStaff.isAdmin) {
      val staffList = loggedInStaff.find_all_staff()

      println("\nWhich Staff Would You Like To Delete?")
      val delEmail = StdIn.readLine()

      println("Are You Sure You Want To Delete This Account?")
      val confirm = StdIn.readLine()

      if(confirm.toUpperCase() == "Y") {
        val delCandidate = new Staff()

        try {
          delCandidate.staffEmailAddress = staffList(delEmail.toInt - 1)(2).toString
          delCandidate.del_staff()

          println("Staff Member Deleted")

          if(delCandidate.staffEmailAddress == loggedInStaff.staffEmailAddress) {
            println("Logged Out: Goodbye!")
            sys.exit()
          }
        }
        catch {
          case e: Exception => println("No Such User Exists")
        }
      }
    }
    else {
      println("Are You Sure You Want To Delete Your Account \n(Enter Y To Confirm)?")
      val confirm = StdIn.readLine()

      if(confirm.toUpperCase() == "Y") {
        loggedInStaff.del_staff()

        println("Staff Member Deleted")
        println("Logged Out: Goodbye!")

        sys.exit()
      }
    }
  }
}
