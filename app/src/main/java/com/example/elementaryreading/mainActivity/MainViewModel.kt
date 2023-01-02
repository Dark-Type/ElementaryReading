package com.example.elementaryreading.mainActivity

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(applicationM: Application) : AndroidViewModel(applicationM) {
    val mJob = Job()
    val mScope = CoroutineScope(Dispatchers.Main + mJob)
    fun playHatefulSpeech() = mScope.launch(Dispatchers.IO) {
        val mMediaPlayer = MediaPlayer.create(
            getApplication(), (getApplication() as Context).resources.getIdentifier(
                "hateful_speech",
                "raw",
                (getApplication() as Context).packageName
            )
        )

        mMediaPlayer.start()
    }
}
