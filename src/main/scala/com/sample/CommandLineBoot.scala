package com.sample

import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition, Situation, WordCheck}
import com.sample.service.WordleService

import scala.io.StdIn.readLine

object CommandLineBoot extends App {

  println("Started game!")
  println("input result in format 0 - miss (gray), 1 - not in position (yellow), 2 - exact (green). For example 20121")
  var i = 1
  var situation = Situation(Nil)
  var word = WordleService.bestWord(situation)
  println(s"№1 ${word}")
  var result = readLine()
  while(result != "22222") {
    if(result.length != 5) println("incorrect result. Input again")
    else {
      val wordCheck = WordCheck(
        word
          .toCharArray
          .zip(result)
          .map{
            case (char, '0') => (char, CharMissed)
            case (char, '1') => (char, CharNotInPosition)
            case (char, '2') => (char, CharInPosition)
          }.toList
      )
      situation = Situation(situation.wordsCheck :+ wordCheck)
      i += 1
      word = WordleService.bestWord(situation)
      println(s"№$i ${word}")
      result = readLine()
    }
  }
}
