package com.sample

import com.sample.domain._
import com.sample.service.WordleService
import org.scalatest.funspec.AnyFunSpec

class FoldSituationTest extends AnyFunSpec {

  it("Test fold results 1") {
    val situation = Situation(
      List(
        WordCheck(
          List(
            ('0', CharNotInPosition),
            ('0', CharMissed),
            ('1', CharMissed),
            ('2', CharNotInPosition),
            ('3', CharMissed)
          )
        ),
        WordCheck(
          List(
            ('4', CharInPosition),
            ('4', CharNotInPosition),
            ('0', CharInPosition),
            ('5', CharInPosition),
            ('6', CharMissed)
          )
        )
      )
    )
    val result = WordleService.processSituation(situation)
    assert(result.inPosition == Map(0 -> '4', 2 -> '0', 3 -> '5'))
    assert(result.deprecatedPositions == Map('0' -> Set(0, 1), '2' -> Set(3), '4' -> Set(1)))
    assert(result.missedChars == Set('1', '3', '6'))
    assert(result.knownChars == Map('0' -> 1, '2' -> 1, '4' -> 2, '5' -> 1))
  }



}
