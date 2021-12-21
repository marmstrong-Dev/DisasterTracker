package com.tools

import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.io._
import com.password4j._
import com.data.Staff
import com.tools.Validator._

object Authenticator {
  // Log In Staff Member
  def staff_login(): Unit = {
    println(s"\n${AnsiColor.BOLD}${AnsiColor.GREEN}Staff Login${AnsiColor.RESET}")
    println("Please Sign In With Email Address And Password\n")

    println("Enter Email Address: ")
    val candidateEmail = StdIn.readLine()
    println("Enter Password: ")
    val candidatePass = StdIn.readLine()

    Logger.getLogger("com").setLevel(Level.OFF);

    //val encPass = Password.hash(candidatePass).withBCrypt()
    //val encPass2 = Password.check("TestPas1s", encPass)

    if(check_for_null(Array[String](candidateEmail, candidatePass))) {
      val loginUser = new Staff(candidatePass, candidateEmail)
    }
  }

  // Register New Staff Member
  def staff_register(): Unit = {
    println(s"\n${AnsiColor.BOLD}${AnsiColor.GREEN}New Staff Member${AnsiColor.RESET}")
    println("Please Fill All Fields\n")

    val regList = new Array[String](5)

    println("First Name: ")
    regList(0) = StdIn.readLine()
    println("Last Name: ")
    regList(1) = StdIn.readLine()
    println("Email Address: ")
    regList(2) = StdIn.readLine()
    println("Create Password: ")
    regList(3) = StdIn.readLine()
    println("Confirm Password: ")
    regList(4) = StdIn.readLine()

    Logger.getLogger("com").setLevel(Level.OFF);

    if(check_for_null(regList) &&
      check_password(regList(3), regList(4)) &&
      check_email(regList(2))) {
      var staffCandidate = new Staff()

      staffCandidate.staffFirstName = regList(0)
      staffCandidate.staffLastName = regList(1)
      staffCandidate.staffEmailAddress = regList(2)
      staffCandidate.staffPassword = Password.hash(regList(3)).withBCrypt().toString

      println(staffCandidate.staffPassword)
    }
    else {
      println(errorMsg)
    }
  }
}
