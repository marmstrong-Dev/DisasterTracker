package com.tools

import com.menus.ActiveEvents.print_event_menu
import com.menus.CategoryMenu.category_opts
import com.menus.CommentMenu.comment_menu_opts
import com.menus.EditProfile.profile_opts

object Router {
  // Case Switch to Determine Menu Route
  def main_router(routOpt: Int): Unit = {
    val opt = routOpt match {
      case 1 => view_active_events()
      case 2 => view_categories()
      case 3 => comment_menu()
      case 4 => edit_profile()
    }
  }

  def view_active_events(): Unit = {
    print_event_menu()
  }

  def view_categories(): Unit = {
    category_opts()
  }

  def comment_menu(): Unit = {
    comment_menu_opts()
  }

  def edit_profile(): Unit = {
    profile_opts()
  }
}
