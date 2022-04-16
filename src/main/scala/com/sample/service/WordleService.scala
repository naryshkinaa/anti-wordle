package com.sample.service

import com.sample.domain.{FoldedSituation, Situation}

import scala.io.Source

object WordleService {

  val inPositionCoefficient = 1
  val deprecatedPositionCoefficient = 1
  val missedCoefficient = 1
  val knownCoefficient = 1

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
    bestWord(foldedSituation)
  }

  def bestWord(foldedSituation: FoldedSituation): String = {
    val subDictionary = dictionary
      .filter(w => foldedSituation.checkWord(w))

    if (subDictionary.size <= 2) return subDictionary.head
    val notFixedPositions = Set(0, 1, 2, 3, 4) -- foldedSituation.inPosition.keys

    val frequencyMap = collection.mutable.Map[(Char, Int), Int]()
    val frequencyTotalMap = collection.mutable.Map[Char, Int]()
    for (
      word <- subDictionary;
      i <- notFixedPositions
    ) {
      val char = word.charAt(i)
      val count = frequencyMap.getOrElse((char, i), 0)
      frequencyMap.put((char, i), count + 1)
      val countTotal = frequencyTotalMap.getOrElse(char, 0)
      frequencyTotalMap.put(char, countTotal + 1)
    }

    def uniqueCharScore(char: Char, pos: Int): Int = {
      if (foldedSituation.inPosition.getOrElse(pos, null) == char) 0
      else {
        if (foldedSituation.inPosition.values.exists(_ == char)) frequencyMap.getOrElse((char, pos), 0)
        else {
          if (foldedSituation.knownChars.contains(char)) {
            frequencyMap.getOrElse((char, pos), 0)
          }
          else {
            notFixedPositions
              .map(p => frequencyMap.getOrElse((char, p), 0))
              .sum + frequencyMap.getOrElse((char, pos), 0) / 2
          }
        }
      }
    }

    def multipleCharScore(char: Char, positions: List[Int]): Int = {
      val bestBySingle = positions.map(p => uniqueCharScore(char, p)).max
      bestBySingle
    }

    def wordScore(word: String): Int = {
      val groups: Map[Char, List[Int]] = word
        .toCharArray
        .zipWithIndex
        .groupBy(_._1)
        .mapValues(_.map(_._2).toList)
        .toMap

      val scored = groups
        .map {
          case (char, pos :: Nil) => uniqueCharScore(char, pos)
          case (char, positions) => multipleCharScore(char, positions)
        }
      scored.sum
    }

    val tmp = dictionary.sortBy(wordScore).reverse
    //    dictionary.maxBy(wordScore)
    tmp.head
  }

  def processSituation(situation: Situation): FoldedSituation = {
    val missed = situation
      .wordsCheck
      .flatMap(_.missedChars)
      .toSet

    val inPosition: Map[Int, Char] =
      situation
        .wordsCheck
        .flatMap(_.inPosition)
        .toMap

    val knownChars = situation
      .wordsCheck
      .flatMap(_.knownChars.toList)
      .groupBy(_._1)
      .view
      .mapValues(f => f.maxBy(_._2)._2)
      .toMap

    val deprecatedPositions =
      situation
        .wordsCheck
        .flatMap(_.deprecatedPositions.toList)
        .groupBy(_._1)
        .view
        .mapValues(f => f.flatMap(_._2).toSet)
        .toMap

    FoldedSituation(inPosition, deprecatedPositions, missed, knownChars)
  }


}
