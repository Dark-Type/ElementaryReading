package com.example.elementaryreading

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Math.random

class GuessTheLetterViewModel(private val applicationGTL: Application) :
    AndroidViewModel(applicationGTL) {
    fun getRandomisedNumber(): Int{
       return (0..7).random()
   }
  private  var lettersList = mutableListOf<Char>()
    fun getRandomLetterFromList(): Char {
        return lettersList[(0..lettersList.size).random()]
    }
   var letterIsFound: Boolean =false
}
