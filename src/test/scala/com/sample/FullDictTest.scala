package com.sample

import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition}
import com.sample.service.WordleService
import com.sample.util.{RunService, WordCheckUtil}
import org.scalatest.funspec.AnyFunSpec

class FullDictTest extends AnyFunSpec{

  it("full dict rate") {
    val result = WordleService
      .dictionary
      .map(RunService.simulateGame(_,false))
    result
      .groupBy(identity)
      .toList
      .sortBy(_._1)
      .foreach(f => println(s"${f._1} - ${f._2.size}"))
    val correct = result.filter(_ != -1)
    println(s"Average ${correct.sum.toDouble / correct.size}")

  }

}
