package com.sample.rest

import akka.http.scaladsl.server.Directives.{as, complete, decodeRequest, entity, path, post}
import akka.http.scaladsl.server.Route
import com.sample.domain._
import com.sample.domain.api.{CharApi, NewWordRequest, NewWordResponse}
import com.sample.service.WordleService
import com.sample.util.JsonUtil

object MainRoute {

  def route: Route =
    path("next-word") {
      post {
        decodeRequest {
          entity(as[String]) {
            request =>
              val parsed = JsonUtil.fromJson[NewWordRequest](request)
              val wordChecks = parsed.words.map(f =>
                WordCheck(
                  f.chars.map {
                    case CharApi(char, 0) => (char.toLower, CharMissed)
                    case CharApi(char, 1) => (char.toLower, CharNotInPosition)
                    case CharApi(char, 2) => (char.toLower, CharInPosition)
                  }
                )
              )

              val newWorld =
                try {
                  WordleService.bestWord(Situation(wordChecks))
                }
                catch {
                  case InvalidDataException => null
                }

              complete(JsonUtil.toJson(NewWordResponse(newWorld)))
          }
        }
      }
    }
}
