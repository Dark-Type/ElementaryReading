package com.example.elementaryreading.fragments.yesNoFragment

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class YesNoMenuViewModel(applicationYN: Application) :
    AndroidViewModel(applicationYN) {
    private val mediaJob = Job()
    private var mMediaPlayer = MediaPlayer()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
    fun playQuestion() = mediaScope.launch(Dispatchers.IO) {
        mMediaPlayer = MediaPlayer.create(
            getApplication(), (getApplication() as Context).resources.getIdentifier(
                "yes_no_menu",
                "raw",
                (getApplication() as Context).packageName
            )
        )

        mMediaPlayer.start()
    }

    fun stopPlaying() {
        mMediaPlayer.stop()
    }


}