package com.example.elementaryreading

import android.app.Application
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*

class RouletteViewModel(applicationRoulette: Application) :
    AndroidViewModel(applicationRoulette) {
    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
    fun playCurrentLetter(currentLetterIndex: Int) = mediaScope.launch(Dispatchers.IO) {
        val mMediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(
                getApplication(),
                Uri.parse("android.resource://com.example.elementaryreading/"+ R.raw.letter0)
            )
            prepare()
        }
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