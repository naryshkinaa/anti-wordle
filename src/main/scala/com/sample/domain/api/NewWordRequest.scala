package com.sample.domain.api

case class NewWordRequest(
                           words: List[WordApi]
                         )

case class WordApi(
                 chars: List[CharApi]
               )

case class CharApi(
                 char: Char,
                 result: Int
               )
