package com.example.elementaryreading

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*


class GuessTheLetterViewModel(applicationGTL: Application) :
    AndroidViewModel(applicationGTL) {
    fun getRandomisedNumber(): Int {
        return (0..7).random()
    }

    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
    fun playCurrentLetter(currentLetterIndex: Int) = mediaScope.launch(Dispatchers.IO) {
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

    fun getRandomLetterFromList(): String {
        return HelperObject.currentLetterList[(0 until HelperObject.currentLetterList.size).random()]
    }

    fun getAbsoluteRandomLetter(): String {
        return HelperObject.absoluteLetterList[(0..32).random()]
    }
}
