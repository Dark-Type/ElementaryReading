package com.example.elementaryreading.helperObject

object HelperObject {
    var firstTime = true

    val gamesList = mutableListOf(
        ("find_the_letter"),
        ("guess_the_letter")

    )
    val absoluteLetterList = mutableListOf(
        "А", ("Б"), ("В"), ("Г"), ("Д"), ("Е"), ("Ё"), ("Ж"), ("З"), ("И"), ("Й"),
        ("К"), ("Л"), ("М"), ("Н"), ("О"), ("П"), ("Р"), ("С"), ("Т"), ("У"), ("Ф"),
        ("Х"), ("Ц"), ("Ч"), ("Ш"), ("Щ"), ("Ь"), ("Ы"), ("Ъ"), ("Э"), ("Ю"), ("Я")
    )
    var currentLetterList = mutableListOf<String>()
    fun getRandomLetter(): String {
        return currentLetterList[(0 until currentLetterList.size).random()]

    }

}