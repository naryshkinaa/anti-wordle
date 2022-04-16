package com.sample.domain

sealed trait CharResult

object CharInPosition extends CharResult
object CharMissed extends CharResult
object CharNotInPosition extends CharResult
