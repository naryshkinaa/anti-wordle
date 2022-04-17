package com.sample.service

import com.sample.domain.{FoldedSituation, Situation}

import scala.io.Source
import scala.util.Random

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
    val doubleCharMap = collection.mutable.Map[Char, Int]()
    val tripleCharMap = collection.mutable.Map[Char, Int]()
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

    subDictionary.foreach {
      word =>
        word
          .toCharArray
          .groupBy(identity)
          .filter(_._2.length >= 2)
          .foreach {
            case (char, list) =>
              val count = doubleCharMap.getOrElse(char, 0)
              doubleCharMap.put(char, count + 1)
              if (list.length == 3) {
                val count2 = tripleCharMap.getOrElse(char, 0)
                tripleCharMap.put(char, count2 + 1)
              }
          }
    }

    val cache = collection.mutable.Map[(Char, Int), Int]()

    def uniqueCharScore(char: Char, pos: Int): Int = {
      cache.getOrElseUpdate(
        (char, pos),
        {
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
      )
    }

    def multipleCharScore(char: Char, positions: List[Int]): Int = {
      val bestBySingle = positions.map(p => uniqueCharScore(char, p)).max
      val byDouble =
        if (foldedSituation.knownChars.getOrElse(char, 0) < positions.length) {
          if (positions.length == 2) doubleCharMap.get(char).map(_ - 1).getOrElse(0)
          else tripleCharMap.getOrElse(char, 0)
        }
        else 0

      bestBySingle + byDouble
    }

    def wordScore(word: String): Int = {
      val groups = collection.mutable.Map[Char, List[Int]]()
      (0 to 4).map {
        i =>
          val char = word.charAt(i)
          val list = groups.getOrElse(char, Nil)
          groups.put(char, list :+ i)
      }

      val scored = groups
        .map {
          case (char, pos :: Nil) => uniqueCharScore(char, pos)
          case (char, positions) => multipleCharScore(char, positions)
        }
      scored.sum
    }

    val byPriority = dictionary.map(w => w -> wordScore(w)).sortBy(_._2)
    val top = 20
//    val top = 1
    if (subDictionary.length == dictionary.length) byPriority(Random.nextInt(top) + dictionary.length - top)._1
    else byPriority.last._1
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

  val stats = collection.mutable.Map[String, Stat]()

  def withStat[A](name: String)(f: => A): A = {
    val start = System.currentTimeMillis()
    val result = f
    val current = stats.getOrElse(name, Stat(0, 0))
    stats.put(name, Stat(current.count + 1, current.total + System.currentTimeMillis() - start))
    result
  }


}

case class Stat(count: Int, total: Long)