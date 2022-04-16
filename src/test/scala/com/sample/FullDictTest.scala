package com.sample

import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition}
import com.sample.service.WordleService
import com.sample.util.{RunService, WordCheckUtil}
import org.scalatest.funspec.AnyFunSpec

class FullDictTest extends AnyFunSpec{

  it("Full dict rating") {
    val start = System.currentTimeMillis()
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
    println(s"Time total (sec): ${(System.currentTimeMillis() - start) / 1000}")

  }

}

//1 - 1
//2 - 106
//3 - 1063
//4 - 1693
//5 - 558
//6 - 60
//7 - 2
//Average 3.8294573643410854
//Time total (sec): 290