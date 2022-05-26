package com.example.elementaryreading

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExplanationViewModel(applicationExp: Application) :
    AndroidViewModel(applicationExp) {

    fun playExplanation() = viewModelScope.launch(Dispatchers.IO) {
        val mMediaPlayer = MediaPlayer.create(
            getApplication(), (getApplication() as Context).resources.getIdentifier(
                "intro",
                "raw",
                (getApplication() as Context).packageName
            )
        )

        mMediaPlayer.start()


    }
}

