package com.sample

import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition}
import com.sample.util.WordCheckUtil
import org.scalatest.funspec.AnyFunSpec

class WordCheckTest extends AnyFunSpec {

  it("серия vs песня") {
    val result = WordCheckUtil.checkWord("серия", "песня")
    assert(result.chars(0)._2 == CharMissed)
    assert(result.chars(1)._2 == CharInPosition)
    assert(result.chars(2)._2 == CharNotInPosition)
    assert(result.chars(3)._2 == CharMissed)
    assert(result.chars(4)._2 == CharInPosition)
  }

  it("агама vs гумма") {
    val result = WordCheckUtil.checkWord("агама", "гумма")
    assert(result.chars(0)._2 == CharNotInPosition)
    assert(result.chars(1)._2 == CharMissed)
    assert(result.chars(2)._2 == CharMissed)
    assert(result.chars(3)._2 == CharInPosition)
    assert(result.chars(4)._2 == CharInPosition)
  }

  it("абвер vs ангар") {
    val result = WordCheckUtil.checkWord("абвер", "парка")
    assert(result.chars(0)._2 == CharMissed)
    assert(result.chars(1)._2 == CharNotInPosition)
    assert(result.chars(2)._2 == CharNotInPosition)
    assert(result.chars(3)._2 == CharMissed)
    assert(result.chars(4)._2 == CharMissed)
  }


}
