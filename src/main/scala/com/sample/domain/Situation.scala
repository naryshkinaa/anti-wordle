package com.sample.domain

case class WordCheck(chars: List[(Char, CharResult)]) {
  def inPosition: Map[Int, Char] = {
    chars
      .zipWithIndex
      .filter(_._1._2 == CharInPosition)
      .map(f => f._2 -> f._1._1)
      .toMap
  }

  def notInPosition: Map[Char, Set[Int]] = {
    chars
      .zipWithIndex
      .filter(_._1._2 == CharNotInPosition)
      .map(f => f._2 -> f._1._1)
      .groupBy(_._2)
      .view
      .mapValues(_.map(_._1).toSet)
      .toMap
  }

  def missed: Set[Char] = {
    chars
      .filter(_._2 == CharMissed)
      .map(_._1)
      .toSet
  }
}

case class Situation(wordsCheck: List[WordCheck])

case class FoldedSituation(
                            inPosition: Map[Int, Char],
                            notInPosition: Map[Char, Set[Int]],
                            missed: Set[Char]
                          ) {
  def checkWord(word: String): Boolean = {
    val missedCondition = word.toCharArray.forall(!missed.contains(_))
    val inCondition =  inPosition.forall(f => word.charAt(f._1) == f._2)
    val notInConditional =  notInPosition.forall(
        f => f._2.forall(p => word.charAt(p) != f._1) && word.toCharArray.contains(f._1)
      )
    missedCondition && inCondition && notInConditional
  }
}
