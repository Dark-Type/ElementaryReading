package com.example.elementaryreading

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel

class FindTheLetterViewModel(applicationFLF: Application) :
    AndroidViewModel(applicationFLF) {
    private val speechRecognizer = SpeechRecognizer(applicationFLF)
    private var txt : String? = null

    fun startListeningFLF() {
        speechRecognizer.startListening()
    }

    fun stopListeningFLF() {
        speechRecognizer.stopListening()
    }

    fun isListeningFLF(): Boolean {
        return speechRecognizer.isListening
    }

    fun setTextFLF() {
        txt = (speechRecognizer.getViewState().value?.spokenText ?: 0) as String

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