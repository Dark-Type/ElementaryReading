package com.example.elementaryreading.fragments.rouletteFragment

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.elementaryreading.letters.EveryLetter
import com.example.elementaryreading.helperObject.HelperObject
import kotlinx.coroutines.*


class RouletteViewModel(applicationRoulette: Application) :
    AndroidViewModel(applicationRoulette) {
    private lateinit var prefs: SharedPreferences
    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)
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

    fun endCoroutine() {
        mediaScope.cancel()
    }

    fun initSharedPref(context: Context) {
        prefs = context.getSharedPreferences("SP", MODE_PRIVATE)
    }

    fun saveLetterList() {
        prefs.saveList()

    }

    fun recoverLetterList() {
        HelperObject.currentLetterList = prefs.getStringSet(
            "letterList",
            null
        )?.toMutableList() ?: HelperObject.currentLetterList
    }

    private fun SharedPreferences.saveList() {
        val set: Set<String> = HelperObject.currentLetterList.toSet()
        edit {
            putStringSet("letterList", set)

            commit()
        }
    }

    fun addRandomLetterToCurrentList(): String {
        addRandomLetterToList(HelperObject.currentLetterList)
        if (HelperObject.currentLetterList.size == 0) {
            return HelperObject.currentLetterList[0]
        }
        return HelperObject.currentLetterList[HelperObject.currentLetterList.size - 1]
    }

    fun generateLettersToShowOnRoulette(listToFill: MutableList<String>) {
        if (HelperObject.currentLetterList.size > 28) {
            for (i in 0..33 - HelperObject.currentLetterList.size) {
                addRandomLetterToList(listToFill)
            }
        }
        for (i in 0..4) {
            addRandomLetterToList(listToFill)
        }
    }

    fun generateLettersToShowOnRouletteFIRST(listToFill: MutableList<String>) {
        if (HelperObject.currentLetterList.size > 28) {
            for (i in 0..33 - HelperObject.currentLetterList.size) {
                addRandomLetterToList(listToFill)
            }
        }
        for (i in 0..30) {
            addRandomLetterToList(listToFill)
        }

    }

    private fun addRandomLetterToList(listToFill: MutableList<String>) {
        val local: String =
            EveryLetter.values()[(0 until EveryLetter.values().size).random()].letter
        if (HelperObject.currentLetterList.indexOf(local) == -1) {
            listToFill.add(local)
            return
        } else {
            addRandomLetterToList(listToFill)
        }
    }

    fun randomizeGame(listToRandomize: MutableList<String>): MutableList<String> {
        val result = listToRandomize[(0..1).random()]
        for (i in 0..8) {
            listToRandomize.add(result)
        }
        return listToRandomize
    }


}