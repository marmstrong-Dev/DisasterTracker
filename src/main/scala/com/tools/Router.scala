package com.tools

import com.menus.ActiveEvents.print_event_menu
import com.menus.CategoryMenu.{category_opts, see_cat_details}
import com.menus.CommentMenu.comment_menu_opts

object Router {
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

  }
}
