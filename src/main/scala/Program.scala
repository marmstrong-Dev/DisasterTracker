import com.data.DbCon.{close_spark, open_spark, spark_update_one, con}
import com.tools.Authenticator.{staff_login, staff_register}
import com.tools.Router.main_router
import com.data.Staff
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import scala.io.{AnsiColor, StdIn}
import com.password4j._


object Program {
  var isLoggedIn: Boolean = false

  // Main Welcome Banner
  def welcome_banner(): Unit = {
    println(s"${AnsiColor.GREEN}${AnsiColor.BOLD}     ***  Disaster Tracker  ***")
    println(s"${AnsiColor.UNDERLINED} Environmental Event Tracking Software \n${AnsiColor.RESET}")

    for (i <- 0 to 12) {
      print("<^>")
    }

    if (!isLoggedIn) {
      auth_menu()
    }
  }

  // Auth Routing Function
  def auth_menu(): Unit = {
    println(
      s"""
      |${AnsiColor.BOLD}Login or Sign Up${AnsiColor.RESET}
      |1.) Log In
      |2.) Sign Up
      |3.) Exit""".stripMargin)
    val authSelection = StdIn.readLine()

    if(authSelection == "1") {
      if(staff_login() != null) {
        isLoggedIn = true
      }
    }
    else if(authSelection == "2") {
      staff_register()
    }
    else if(authSelection == "3") {
      isLoggedIn = false
      print("\nExiting: Goodbye!")
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
    val optSet = Set("1", "2", "3", "4")

    if(menuSelection == "5") {
      isLoggedIn = false
      println("Logout Successful. Goodbye.")
    }
    else if(optSet.contains(menuSelection)) {
      main_router(menuSelection.toInt)
    }
    else {
      println("Invalid Selection")
    }
  }

  def main(args: Array[String]): Unit = {
    //spark_opener()
    //welcome_banner()

    /*val spark = SparkSession
      .builder()
      .appName("Disaster Tracker")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    println("Adding Entry")

    spark.sql("USE ProjectOne")
    spark.sql(s"INSERT INTO Staff PARTITION (is_supervisor = 1) VALUES ('Sys','Admin', 'sys.admin@address.com', '${Password.hash("TestPass").withBCrypt().getResult}')")
    spark.sql("SELECT * FROM Staff").show()
    spark.close()*/
    con = open_spark()

    //spark_update_one("marms@address.com")
    while(!isLoggedIn) {
      welcome_banner()
    }
    while(isLoggedIn) {
      main_menu()
    }

    close_spark()
    //main_menu()
  }
}
