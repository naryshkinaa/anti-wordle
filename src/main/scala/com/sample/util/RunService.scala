package com.sample.util

import com.sample.domain.Situation
import com.sample.service.WordleService

object RunService {
  private val maxIterations = 10

  def simulateGame(answer: String, isLog: Boolean) = {
    def nextStep(situation: Situation, iteration: Int): Int = {
      if (iteration > maxIterations){
        if(isLog) println("Can't find solution")
        -1
      }
      else {
        val result = WordleService.bestWord(situation)
        if(isLog) println(s"№ $iteration $result")
        if (answer != result) {
          val checkResult = WordCheckUtil.checkWord(answer, result)
          nextStep(Situation(situation.wordsCheck :+ checkResult), iteration + 1)
        } else {
          iteration
        }

      }
    }

    if(isLog) println(s"Hidden word: $answer")
    val result = nextStep(Situation(Nil), 1)
    if(result >= 7) println(s"Hidden word: $answer")
    result
  }

}
