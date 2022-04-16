package com.sample

import com.sample.domain.{CharInPosition, CharMissed, CharNotInPosition}
import com.sample.service.WordleService
import com.sample.util.{RunService, WordCheckUtil}
import org.scalatest.funspec.AnyFunSpec
import scala.collection.parallel.CollectionConverters._

class FullDictTest extends AnyFunSpec{

  it("Full dict rating") {
    val start = System.currentTimeMillis()
    val result = WordleService
      .dictionary
      .par
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
//2 - 92
//3 - 1051
//4 - 1819
//5 - 481
//6 - 36
//7 - 3
//Average 3.805914441573356
//Time total (sec): 35