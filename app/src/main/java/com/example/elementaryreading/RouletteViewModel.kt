package com.example.elementaryreading

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class RouletteViewModel(applicationRoulette: Application) :
    AndroidViewModel(applicationRoulette) {
    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
    fun playCurrentLetter(currentLetterIndex: Int) =viewModelScope.launch(Dispatchers.IO){
        val mMediaPlayer = MediaPlayer.create(
            getApplication(), (getApplication() as Context).resources.getIdentifier(
                "letter$currentLetterIndex",
                "raw",
                (getApplication() as Context).packageName
            )
        )

        mMediaPlayer.start()
    }

    fun endCoroutine() {
        mediaScope.cancel()
    }
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
    fun generateLettersToShowOnRouletteFIRST(listToFill: MutableList<String>){
        if (HelperObject.currentLetterList.size > 28) {
            for (i in 0..33 - HelperObject.currentLetterList.size) {
                addRandomLetterToList(listToFill)
            }
        }
        for (i in 0..30) {
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
        val result = listToRandomize[(0..2).random()]
        if (listToRandomize.size == 3) {
            listToRandomize[2] = result
        } else {
            listToRandomize.add(result)
        }


    }


}