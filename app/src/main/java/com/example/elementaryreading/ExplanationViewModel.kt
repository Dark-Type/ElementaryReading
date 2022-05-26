package com.example.elementaryreading

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExplanationViewModel (applicationExp: Application) :
    AndroidViewModel(applicationExp) {
    private val mediaJob = Job()
    private var mMediaPlayer = MediaPlayer()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
//    fun playExplanation() = mediaScope.launch(Dispatchers.IO) {
//        mMediaPlayer = MediaPlayer.create(
//            getApplication(), (getApplication() as Context).resources.getIdentifier(
//                "explanation",
//                "raw",
//                (getApplication() as Context).packageName
//            )
//        )
//
//        mMediaPlayer.start()
//    }

}