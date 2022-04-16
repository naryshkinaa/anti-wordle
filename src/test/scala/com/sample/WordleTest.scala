package com.sample

import com.sample.util.RunService
import org.scalatest.funspec.AnyFunSpec

class WordleTest extends AnyFunSpec {

  val words = Map(
    "каска" -> 6,
    "тутти" -> 5,
    "эрзац" -> 5,
    "щетка"-> 6,
    "филин" -> 4,
    "фаска" -> 6,
    "хамка" -> 6,
    "цацка" -> 6,
    "чабан" -> 6,
    "шашка" -> 6,
    "щучка" -> 6,
  )

  words.foreach(
    f => it(s"Check word ${f._1}. Max attemps ${f._2}") {
      val result = RunService.simulateGame(f._1, true)
      assert(result <= f._2)
    }
  )
  it("Simple word run") {
    val result = RunService.simulateGame("шашка", true)
  }
}
