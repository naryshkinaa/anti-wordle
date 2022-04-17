package com.sample


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.concat
import com.sample.rest.{MainRoute, UIRoute}

object RestBoot extends App {
  implicit val system: ActorSystem = ActorSystem("simple-http")

  val bindingFuture =
    Http()
      .newServerAt("0.0.0.0", 8080)
      .bind(
        concat(
          MainRoute.route,
          UIRoute.route
        )
      )

}