package com.sample.service

import com.sample.domain.{FoldedSituation, Situation}

import scala.io.Source

object WordleService {

  val dict = "russian_nouns.txt"
  val dictionary: List[String] = {
    val resource = Source.fromResource(dict)
    resource
      .getLines
      .filter(_.length == 5)
      .map(
        _.replace("ё", "е")
      )
      .toList
  }

  def bestWord(situation: Situation): String = {
    val foldedSituation = processSituation(situation)
    val subDictionary = dictionary
      .filter(w => foldedSituation.checkWord(w))

    val frequencyMap = collection.mutable.Map[Char, Int]()
    for (
      word <- subDictionary;
      char <- word.toCharArray
    ) {
      val count = frequencyMap.getOrElse(char, 0)
      frequencyMap.put(char, count + 1)
    }

    def wordScore(word: String): Int = {
      word
        .toCharArray
        .distinct
        .map(frequencyMap(_)).sum
    }

    subDictionary.maxBy(wordScore)
  }

  def processSituation(situation: Situation): FoldedSituation = {
    val missed = situation
      .wordsCheck
      .flatMap(_.missed)
      .toSet
    val inPosition: Map[Int, Char] =
      situation
        .wordsCheck
        .flatMap(_.inPosition)
        .toMap
    val notInPosition =
      situation
        .wordsCheck
        .flatMap(_.notInPosition.toList)
        .groupBy(_._1)
        .view
        .mapValues(f => f.flatMap(_._2).toSet)
        .toMap

    FoldedSituation(inPosition, notInPosition, missed)
  }


}
