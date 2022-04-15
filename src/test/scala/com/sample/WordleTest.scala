package com.sample

import com.sample.util.RunService
import org.scalatest.funspec.AnyFunSpec

class WordleTest extends AnyFunSpec {

  it("Simple word run агама") {
    println(RunService.simulateGame("агама", true))
  }
  it("Simple word run чайка") {
    println(RunService.simulateGame("чайка", true))
  }

}
