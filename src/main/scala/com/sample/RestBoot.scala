package com.sample


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.concat
import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition, Situation, WordCheck}
import com.sample.rest.{MainRoute, UIRoute}
import com.sample.service.WordleService
import com.sample.service.WordleService.bestWord
import com.sample.util.RunService

object RestBoot extends App {
  implicit val system: ActorSystem = ActorSystem("simple-http")

  val bindingFuture =
    Http()
      .newServerAt("localhost", 8080)
      .bind(
        concat(
          MainRoute.route,
          UIRoute.route
        )
      )

}