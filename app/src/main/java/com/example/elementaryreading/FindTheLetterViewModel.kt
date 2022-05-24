package com.example.elementaryreading
import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*


class FindTheLetterViewModel(applicationFLF: Application) :
    AndroidViewModel(applicationFLF) {
    val speechRecognizer = SpeechRecognizer(applicationFLF)
    private var txt: String? = null
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
    fun startListeningFLF() {
        speechRecognizer.startListening()
    }

    fun stopListeningFLF() {
        speechRecognizer.stopListening()
    }

    fun isListeningFLF(): Boolean {
        return speechRecognizer.isListening
    }

    fun render(uiOutput: SpeechRecognizer.ViewState?) {
        if (uiOutput == null) return
        txt = uiOutput.spokenText
    }

    fun checkTheLetterFLF(textView: TextView): Boolean {
        if (txt != null) {
            if (txt!!.indexOf(textView.text.toString(), 0, false) != -1) {
                txt = null
                return true
            }
        }
        return false
    }
}