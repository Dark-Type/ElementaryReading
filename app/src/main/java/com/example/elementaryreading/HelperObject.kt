package com.example.elementaryreading

object HelperObject {
    val gamesList = mutableListOf(
        ("tetris"),
        ("three_in_line"),
        ("find_the_letter"),
        ("guess_the_letter"),
        ("slice_the_letter")
    )
    var currentLetterList = mutableListOf<String>()
    fun getRandomLetter(): String {
        return currentLetterList[(0..currentLetterList.size).random()]

    }

}