package com.example.elementaryreading

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.*
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.abs

class SpeechRecognizer(application: Application) :
    RecognitionListener {

    data class ViewState(
        var spokenText: String,
        val isListening: Boolean,
        val error: String?,
        val rmsDbChanged: Boolean = false
    )

    private var viewState: MutableLiveData<ViewState>? = null
    private var previousRmsdB = 0f
    private val speechRecognizer: SpeechRecognizer =
        createSpeechRecognizer(application.applicationContext).apply {
            Log.d("test", "added recognition listener")
            setRecognitionListener(this@SpeechRecognizer)
        }

    private val recognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }

    var isListening = false
        get() = viewState?.value?.isListening ?: false


    fun getViewState(): LiveData<ViewState> {
        if (viewState == null) {
            viewState = MutableLiveData()
            viewState?.value = initViewState()
        }

        return viewState as MutableLiveData<ViewState>
    }

    private fun initViewState() = ViewState(spokenText = "", isListening = false, error = null)

    fun startListening() {
        Log.d("test", "listening started")
        speechRecognizer.startListening(recognizerIntent)
        notifyListening(isRecording = true)
    }

    fun stopListening() {
        Log.d("test", "listening stopped")
        speechRecognizer.stopListening()
        notifyListening(isRecording = false)
    }

    private fun notifyListening(isRecording: Boolean) {
        viewState?.value = viewState?.value?.copy(isListening = isRecording, rmsDbChanged = false)
    }

    private fun updateResults(speechBundle: Bundle?) {
        val userSaid = speechBundle?.getStringArrayList(RESULTS_RECOGNITION)
        Log.d("test", "result update")

        viewState?.value =
            viewState?.value?.copy(spokenText = userSaid?.get(0) ?: "", rmsDbChanged = false)
    }


    override fun onPartialResults(results: Bundle?) {
        Log.d("test", "partialResult")
        Log.d(
            "test",
            (results?.getStringArrayList("android.speech.extra.UNSTABLE_TEXT")
                .toString() + "partialResult")
        )
        updateResults(speechBundle = results)
    }

    override fun onResults(results: Bundle?) {
        Log.d("test", "result")
        Log.d("test", results?.getStringArrayList(RESULTS_RECOGNITION).toString() + "result")
        updateResults(speechBundle = results)
    }

    override fun onEndOfSpeech() {
        Log.d("test", "speech end")

        notifyListening(isRecording = false)
    }

    override fun onError(errorCode: Int) {
        viewState?.value = viewState?.value?.copy(
            error = when (errorCode) {
                ERROR_AUDIO -> "error_audio_error"
                ERROR_CLIENT -> "error_client"
                ERROR_INSUFFICIENT_PERMISSIONS -> "error_permission"
                ERROR_NETWORK -> "error_network"
                ERROR_NETWORK_TIMEOUT -> "error_timeout"
                ERROR_NO_MATCH -> "error_no_match"
                ERROR_RECOGNIZER_BUSY -> "error_busy"
                ERROR_SERVER -> "error_server"
                ERROR_SPEECH_TIMEOUT -> "error_timeout"
                else -> "error_unknown"
            }, rmsDbChanged = false
        )
    }

    override fun onRmsChanged(rmsdB: Float) {
        if (rmsdB > 4 && diffRms(newRms = rmsdB, previousRms = previousRmsdB) > 1) {
            previousRmsdB = rmsdB
            viewState?.value = viewState?.value?.copy(rmsDbChanged = true)
        }
    }

    private fun diffRms(newRms: Float, previousRms: Float): Int = abs(previousRms - newRms).toInt()

    override fun onReadyForSpeech(p0: Bundle?) {
        Log.d("test", "ready for speech")
    }

    override fun onBufferReceived(p0: ByteArray?) {}
    override fun onEvent(p0: Int, p1: Bundle?) {}
    override fun onBeginningOfSpeech() {}
}