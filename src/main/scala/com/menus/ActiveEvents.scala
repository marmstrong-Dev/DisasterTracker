package com.menus

import scala.io._
import com.tools.Fetcher._

object ActiveEvents {
  // Sub Router For Events
  def print_event_menu(): Unit = {
    println("\nWhat Would You Like To See: \n1.) View All Events\n2.) View By Category")
    val selector = StdIn.readLine()

    if(selector == "1") {
      print_all()
    }
    else if(selector == "2") {
      print_by_cat()
    }
    else {
      println("Invalid Selection")
    }
  }

  def print_all(): Unit = {
    val url = "https://eonet.gsfc.nasa.gov/api/v2.1/events?status=open"
    fetch_data(url)
  }

  def print_by_cat(): Unit = {
    println("Select A Category:")
  }
  /*
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer
    implicit val execute = system.dispatcher

    import system.dispatcher

    val req = HttpRequest(
      method = HttpMethods.GET,
      uri = "https://eonet.gsfc.nasa.gov/api/v2.1/events?status=open",
      entity = HttpEntity(
        ContentTypes.`application/json`,
        s""
      )
    )

    val resFuture = Http().singleRequest(req)
    val entityFuture = resFuture.flatMap(res => res.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String).foreach(x => println(x))
    //implicit val executionContext = system.executionContext
    //val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://akka.io"))

    val url = "https://eonet.gsfc.nasa.gov/api/v2.1/events?status=open"
    val result = scala.io.Source.fromURL(url).mkString

    println(result)
   */
}
