package com.example.elementaryreading

import android.content.Context
import android.graphics.Point
import android.provider.SyncStateContract
import android.view.SurfaceHolder
import android.view.SurfaceView

class GamePanel(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
    private lateinit var thread: SliceTheLetterThread
    private val user: Player
    private var userPoint: Point
    private var fruitManager: FruitManager
    private var highScore = SyncStateContract.Constants.PREF.getInt("key", 0)
    private var gameOver = false

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = SliceTheLetterThread(getHolder(), this)
        thread.setRunning(true)
        thread.start()
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }

}
