package com.sample

import com.sample.service.WordleService
import com.sample.util.RunService
import org.scalatest.funspec.AnyFunSpec

import scala.collection.parallel.CollectionConverters._

class FullDictTest extends AnyFunSpec {

  it("Full dict rating") {
    val start = System.currentTimeMillis()
    val result = WordleService
      .dictionary
      .par
      .map(RunService.simulateGame(_, false))
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

//2 - 91
//3 - 1086
//4 - 1686
//5 - 554
//6 - 66
//Average 3.8329026701119724
//Time total (sec): 37