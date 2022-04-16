package com.sample

import com.sample.domain.FoldedSituation
import com.sample.service.WordleService
import org.scalatest.funspec.AnyFunSpec

class BestWordTest extends AnyFunSpec{
  it("Test fold results 2") {
    val foldedSituation = FoldedSituation(
      Map(1 -> 'р', 3 -> 'а'),
      Map(),
      Set('у', 'к', 'е', 'н', 'г', 'х', 'ы', 'в', 'п', 'о', 'л', 'д', 'с', 'и', 'т', 'ь', 'б'),
      Map('р' -> 1, 'а' -> 1)
    )
    println(WordleService.bestWord(foldedSituation))
  }
  it("Test fold results 3") {
    val foldedSituation = FoldedSituation(
      Map(3 -> 'к', 4 -> 'а', 1 -> 'е', 2 -> 'т'),
      Map('а' -> Set(1), 'т' -> Set(4)),
      Set('л', 'п', 'у', 'о', 'я','н', 'м', 'р'),
      Map('а' -> 1, 'к' -> 1, 'т' -> 1, 'е' -> 1)
    )
    val result = WordleService.bestWord(foldedSituation)
//    assert(result == "")
  }
}
