package com.sample


import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition, Situation, WordCheck}
import com.sample.service.WordleService
import com.sample.service.WordleService.bestWord
import com.sample.util.RunService

object Boot extends App {

  println(RunService.simulateGame("агама", true))
//

}