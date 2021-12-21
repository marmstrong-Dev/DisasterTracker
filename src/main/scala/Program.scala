import scala.io._
import com.tools.Authenticator._

object Program {
  var isLoggedIn = false

  // Main Welcome Banner
  def welcome_banner(): Unit ={
    println(s"${AnsiColor.GREEN}${AnsiColor.BOLD}    ***  Disaster Tracker  ***")
    println(s"${AnsiColor.UNDERLINED} Environmental Event Tracking Software \n${AnsiColor.RESET}")

    for(i <- 0 to 12) {
      print("<^>")
    }

    if(!isLoggedIn) {
      auth_menu()
    }
  }

  // Auth Routing Function
  def auth_menu(): Unit ={
    println(
      s"""
        |${AnsiColor.BOLD}Login or Sign Up${AnsiColor.RESET}
        |
        |1.)  Login
        |2.)  New Account
        |3.)  Exit""".stripMargin)
    val authSelection = StdIn.readLine()

    if(authSelection == "1") {
      staff_login()
    }
    else if(authSelection == "2") {
      staff_register()
    }
    else if(authSelection == "3") {
      isLoggedIn = false
      print("\nLogged Out: Goodbye!")
      sys.exit()
    }
    else {
      println("Invalid Option")
    }
  }

  // Pull Up Main Menu - Requires Login
  def main_menu(): Unit ={
    println(s"${AnsiColor.GREEN}${AnsiColor.BOLD}Welcome${AnsiColor.RESET}\nWhat Would You Like To Do?")

    println(
      """
        |1.) View Active Events
        |2.) View Event Categories
        |3.) Add / Edit Comments
        |4.) Edit My Profile
        |5.) Logout and Exit""".stripMargin)
    val menuSelection = StdIn.readLine()
  }

  def main(args: Array[String]): Unit = {
    welcome_banner()
    //main_menu()

    if(isLoggedIn) {
      println("Logged In")
    }
  }
}
