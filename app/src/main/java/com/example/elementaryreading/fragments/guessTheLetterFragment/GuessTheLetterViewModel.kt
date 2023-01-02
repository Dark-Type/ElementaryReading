package com.example.elementaryreading.fragments.guessTheLetterFragment

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.elementaryreading.helperObject.HelperObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GuessTheLetterViewModel(applicationGTL: Application) :
    AndroidViewModel(applicationGTL) {
    fun getRandomisedNumber(): Int {
        return (0..7).random()
    }

    fun playCurrentLetter(currentLetterIndex: Int) = viewModelScope.launch(Dispatchers.IO) {
        val mMediaPlayer = MediaPlayer.create(
            getApplication(), (getApplication() as Context).resources.getIdentifier(
                "letter$currentLetterIndex",
                "raw",
                (getApplication() as Context).packageName
            )
        )

        mMediaPlayer.start()
    }



    fun getRandomLetterFromList(): String {
        return HelperObject.currentLetterList[(0 until HelperObject.currentLetterList.size).random()]
    }

    fun getAbsoluteRandomLetter(): String {
        return HelperObject.absoluteLetterList[(0..32).random()]
    }
}
