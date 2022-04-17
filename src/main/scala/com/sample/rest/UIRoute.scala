package com.sample.rest

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import com.sample.domain._
import akka.http.scaladsl.server.Directives._
import com.sample.domain.api.{CharApi, NewWordRequest, NewWordResponse}
import com.sample.service.WordleService
import com.sample.util.JsonUtil

object UIRoute {

  def route: Route =
    get {
      path("ui") {
        getFromResource("ui/index.html")
      } ~ pathPrefix("ui") {
        getFromResourceDirectory("ui")
      }
    }
}
