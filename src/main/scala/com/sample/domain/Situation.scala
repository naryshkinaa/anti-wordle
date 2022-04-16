package com.sample.domain

case class WordCheck(chars: List[(Char, CharResult)]) {

  private def exist(char: Char): Boolean = {
    chars.exists(f => f._1 == char && (f._2 == CharInPosition || f._2 == CharNotInPosition))
  }
  def inPosition: Map[Int, Char] = {
    chars
      .zipWithIndex
      .filter(_._1._2 == CharInPosition)
      .map(f => f._2 -> f._1._1)
      .toMap
  }

  def deprecatedPositions: Map[Char, Set[Int]] = {
    chars
      .zipWithIndex
      .filter(f => f._1._2 == CharNotInPosition || f._1._2 == CharMissed && exist(f._1._1) )
      .map(f => f._2 -> f._1._1)
      .groupBy(_._2)
      .view
      .mapValues(_.map(_._1).toSet)
      .toMap
  }

  def missedChars: Set[Char] = {
    chars
      .filter(c => c._2 == CharMissed && !exist(c._1))
      .map(_._1)
      .toSet
  }

  def knownChars: Map[Char, Int] = {
    val allChars = inPosition.values ++ chars
      .filter(_._2 == CharNotInPosition)
      .map(_._1)
    allChars
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap
  }
}

case class Situation(wordsCheck: List[WordCheck])

case class FoldedSituation(
                            inPosition: Map[Int, Char],
                            deprecatedPositions: Map[Char, Set[Int]],
                            missedChars: Set[Char],
                            knownChars: Map[Char, Int]
                          ) {
  def checkWord(word: String): Boolean = {
    val missedCondition = word.toCharArray.forall(!missedChars.contains(_))
    val inCondition = inPosition.forall(f => word.charAt(f._1) == f._2)
    val notInConditional = deprecatedPositions.forall(f => f._2.forall(p => word.charAt(p) != f._1))
    val charCount = word.toCharArray.groupBy(identity).mapValues(_.length).toMap
    val knownCharsConditional = knownChars.forall(f => f._2 <= charCount.getOrElse(f._1, 0))
    missedCondition && inCondition && notInConditional && knownCharsConditional
  }
}
