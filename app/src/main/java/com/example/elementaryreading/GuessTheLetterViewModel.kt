package com.example.elementaryreading

import android.app.Application
import androidx.lifecycle.AndroidViewModel


class GuessTheLetterViewModel(applicationGTL: Application) :
    AndroidViewModel(applicationGTL) {
    fun getRandomisedNumber(): Int {
        return (0..7).random()
    }

    private var lettersList = mutableListOf<Char>()
    fun getRandomLetterFromList(): Char {
        if (lettersList.size == 0) {
            if ((0..10).random() > 3) {
                return 'А'
            }
            return 'Б'
        }
        return lettersList[(0..lettersList.size).random()]
    }
}
