package com.sample


import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition, Situation, WordCheck}
import com.sample.service.WordleService
import com.sample.service.WordleService.bestWord
import com.sample.util.RunService

object Boot extends App {

  println(RunService.simulateGame("агама", true))
//  val test = Situation(
//    List(
//      WordCheck(
//        List(
//          ('к', CharNotInPosition),
//          ('р', CharMissed),
//          ('о', CharMissed),
//          ('а', CharNotInPosition),
//          ('т', CharNotInPosition)
//        )
//      ),
//      WordCheck(
//        List(
//          ('л', CharMissed),
//          ('е', CharMissed),
//          ('т', CharInPosition),
//          ('к', CharInPosition),
//          ('а', CharInPosition)
//        )
//      )
//    )
//  )

}