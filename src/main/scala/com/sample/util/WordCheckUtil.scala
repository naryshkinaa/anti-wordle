package com.sample.util

import com.sample.domain.{CharInPosition, CharMissed, CharMissedExist, CharNotInPosition, WordCheck}

object WordCheckUtil {
  def checkWord(answer: String, word: String): WordCheck = {
    val answerChars = answer.toCharArray
    val wordsChars = word.toCharArray
    val withoutCorrect =
      wordsChars
        .zip(answerChars)
        .filter(f => f._1 != f._2)
        .map(_._2)
    val charsResult = wordsChars
      .zip(answerChars)
      .map {
        case (char1, char2) =>
          val result =
            if (char1 == char2) CharInPosition
            else {
              if (withoutCorrect.contains(char1)) CharNotInPosition
              else {
                if(answerChars.contains(char1)) CharMissedExist
                else CharMissed
              }
            }
          (char1, result)
      }
    WordCheck(charsResult.toList)
  }
}
