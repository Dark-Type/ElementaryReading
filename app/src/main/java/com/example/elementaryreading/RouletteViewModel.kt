package com.example.elementaryreading

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class RouletteViewModel(applicationRoulette: Application) :
    AndroidViewModel(applicationRoulette) {
    var firstTime: Boolean = true
    fun addRandomLetterToCurrentList(): String {
        addRandomLetterToList(HelperObject.currentLetterList)
        if (HelperObject.currentLetterList.size == 0) {
            return HelperObject.currentLetterList[0]
        }
        return HelperObject.currentLetterList[HelperObject.currentLetterList.size - 1]
    }

    fun generateLettersToShowOnRoulette(listToFill: MutableList<String>) {
        if (HelperObject.currentLetterList.size > 28) {
            for (i in 0..33 - HelperObject.currentLetterList.size) {
                addRandomLetterToList(listToFill)
            }
        }
        for (i in 0..4) {
            addRandomLetterToList(listToFill)
        }
    }

    private fun addRandomLetterToList(listToFill: MutableList<String>) {
        val local: String = EveryLetter.values()[(0 until EveryLetter.values().size).random()].letter
        if (HelperObject.currentLetterList.indexOf(local) == -1) {
            listToFill.add(local)
            return
        } else {
            addRandomLetterToList(listToFill)
        }
    }

    fun randomizeGame(listToRandomize: MutableList<String>) {
        val result = listToRandomize[(0..4).random()]
        if (listToRandomize.size == 6) {
            listToRandomize[5] = result
        } else {
            listToRandomize.add(result)
        }


    }


}