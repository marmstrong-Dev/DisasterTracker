package com.data

import org.apache.spark.sql.SparkSession
import com.data.DbCon.con

// Class Model For App Users
class Staff (
              var staffFirstName: String,
              var staffLastName: String,
              var staffEmailAddress: String,
              var staffPassword: String,
              var isAdmin: Boolean
            ) {
  def this() = {this("", "", "", "", false)}

  def this(staffEmail: String, staffPass: String) = {
    this("", "", staffEmail, staffPass, false)
  }

  def lookup_staff(): Unit = {
    val lookupQuery = s"SELECT * FROM Staff WHERE staff_email_address = '${this.staffEmailAddress}'"

    val staffData = DbCon.spark_lookup_one(lookupQuery)

    try {
      this.staffFirstName = staffData(0)
      this.staffLastName = staffData(1)
      this.staffPassword = staffData(3)
      if(staffData(4).toInt == 1) {
        this.isAdmin = true
      }
      else {
        this.isAdmin = false
      }
    }
    catch {
      case e: Exception => println("Account Doesn't Exist")
    }
  }

  def add_staff(): Unit = {
    val addStaffQuery =
      s"""
      INSERT INTO Staff
      PARTITION (is_supervisor = 0) VALUES (
        '${this.staffFirstName}',
        '${this.staffLastName}',
        '${this.staffEmailAddress}',
        '${this.staffPassword}')
      """

    DbCon.spark_add_one(addStaffQuery)
  }

  def edit_staff(): Unit = {
    val editStaffQuery =
      s"""
      UPDATE Staff SET
      WHERE staff_email_address = ${this.staffEmailAddress}
      """

    DbCon.spark_update_one(editStaffQuery)
  }

  def make_admin(): Unit = {
    val makeAdminQuery = s"UPDATE Staff SET is_supervisor = 1 WHERE staff_email_address = ${this.staffEmailAddress}"
  }
}
