package com.example.elementaryreading

import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel


class FindTheLetterViewModel(applicationFLF: Application) :
    AndroidViewModel(applicationFLF) {
    val speechRecognizer = SpeechRecognizer(applicationFLF)
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


    fun render (uiOutput: SpeechRecognizer.ViewState?){
        if(uiOutput==null)return
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