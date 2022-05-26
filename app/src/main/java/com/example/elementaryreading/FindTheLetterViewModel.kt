package com.example.elementaryreading

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*


class FindTheLetterViewModel(applicationFLF: Application) :
    AndroidViewModel(applicationFLF) {

    data class Events(
        var gameEnd: Boolean,
        var endOfSpeech: Boolean
    )

    private var eventLiveData: MutableLiveData<Events>? = null
    fun getEventLiveData(): LiveData<Events> {

        if (eventLiveData == null) {
            eventLiveData = MutableLiveData()
            eventLiveData?.value = initELV()
        }

        return eventLiveData as MutableLiveData<Events>
    }

    private fun initELV() = Events(gameEnd = false, endOfSpeech = false)

    val speechRecognizer = SpeechRecognizer(applicationFLF)
    private var txt: String? = null
    private val mediaJob = Job()
    private var currentLetter: String = ""
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

    fun changeCurrentLetter(curL: String) {
        currentLetter = curL
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
        if (checkTheLetterFLF()) {
           eventLiveData?.value =  eventLiveData?.value?.copy(gameEnd = true,endOfSpeech = true)
        }
      else  if (!uiOutput.isListening) {
            eventLiveData?.value =  eventLiveData?.value?.copy(gameEnd = false,endOfSpeech = true)
        }
        else{
            eventLiveData?.value =  eventLiveData?.value?.copy(gameEnd = false,endOfSpeech = false)
        }

    }

    fun checkTheLetterFLF(): Boolean {
        if (txt != null) {
            if (txt!!.indexOf(currentLetter, 0, false) != -1) {
                txt = null
                return true
            }
        }
        return false
    }
}