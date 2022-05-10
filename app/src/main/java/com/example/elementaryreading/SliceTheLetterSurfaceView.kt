package com.example.elementaryreading

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sqrt

object Constants {
    var SCREEN_WIDTH = 0
    var SCREEN_HEIGHT = 0
    var PREF: SharedPreferences? = null
}

interface GameObject {
    fun draw(canvas: Canvas?)
    fun update()
}

class Letter(
    rectHeight: Int, //Color used for instantiating rect object
    val color: Int,
    startX: Int,
    startY: Int,
    playerSize: Int, val type: Int
) : GameObject {


    val rectangle: Rect = Rect(startX, startY, startX + playerSize, startY + rectHeight)
    fun makeLetterFall(y: Float) {
        rectangle.top += y.toInt()
        rectangle.bottom += y.toInt()
    }

    //returns true when player touched
    fun collisionDetection(player: SliceTheLetterPlayer): Boolean {
        return Rect.intersects(rectangle, player.rectangle)
    }

    override fun draw(canvas: Canvas?) {
        val paint = Paint()
        paint.color = color
        canvas!!.drawRect(rectangle, paint)
    }

    override fun update() {}

}


class LetterManager(playerSize: Int, letterLocation: Int, letterHeight: Int, color: Int) {
    private val letters: ArrayList<Letter> = TODO()
    private val letterLocation: Int = 0
    private val playerSize: Int = 0
    private val letterHeight: Int = 0
    private var misses = 0
    private val color = 0
    private var start: Long = 0
    private val initialization: Long = 0
    fun collisionDetection(player: SliceTheLetterPlayer?): Boolean {

        //Check each letter on screen for detection
        for (l in letters) {
            if (player?.let { l.collisionDetection(it) } == true) {

                //Game over, hit wrong letter
                if (l.type == 2) {

                    return true
                }


                //Remove from the arraylist
                letters.remove(l)
            }
        }
        return false
    }

    //Assigns next falling letter to a random type
    private fun determineLetterType(): Int {
        val value = (0..100).random()
        var type = -1
        type = if (value in 0..30) {
            1 //here would be correct letter
        } else {
            2 // here would be incorrect letters
        }
        return type
    }

    private fun populateLetters() {

        //Starting Y position for the fruit
        var currentY = -5 * Constants.SCREEN_HEIGHT / 4
        while (currentY < 0) {

            //Determine where horizontally to place the letter
            val xStart = (Math.random() * (Constants.SCREEN_WIDTH - playerSize)).toInt()

            //Determines which type of letter is spawned
            val type = determineLetterType()

            //Add to the array list
            letters.add(Letter(letterHeight, color, xStart, currentY, playerSize, type))
            currentY += letterHeight + letterLocation
        }


    }

    fun update(): Boolean {
        val timeElapsed = (System.currentTimeMillis() - start).toInt()
        start = System.currentTimeMillis()

        //Determine fall speed of the letter
        //Value gets larger as the game progresses but is in range of a minute anyway
        val speed = sqrt(1 + (start - initialization) / 1000.0)
            .toFloat() * Constants.SCREEN_HEIGHT / 10000.0f

        //make each letter fall
        for (letter in letters) {
            letter.makeLetterFall(speed * timeElapsed)
        }
        //Letter has made it to the bottom of the screen, add a strike to the count
        if (letters[letters.size - 1].rectangle.top >= Constants.SCREEN_HEIGHT) {


            //Letter is correct, mark a penalty
            if (letters[letters.size - 1].type != 2) {
                misses++
            }

            //Game Over
            if (misses == 3) {
                return true
            }

            //Remove from the array list
            letters.remove(letters[letters.size - 1])
        }

        //Add a new letter to be spawned
        val type = determineLetterType()
        val xStart = (Math.random() * (Constants.SCREEN_WIDTH - playerSize)).toInt()
        letters.add(
            0, Letter(
                letterHeight, color, xStart,
                letters[0].rectangle.top - letterHeight - letterLocation,
                playerSize, type
            )
        )
        return false
    }

    fun draw(canvas: Canvas) {

        for (letter in letters) {
            letter.draw(canvas)
            val text: String
            when (letter.type) {
                1 -> text = HelperObject.currentLetterList[HelperObject.currentLetterList.size]
                2 -> text = HelperObject.getRandomLetter()

            }
            canvas.drawText(
                text,
                (Math.random() * (Constants.SCREEN_WIDTH - playerSize)).toFloat(),
                null,
                letter.rectangle,
                Paint()
            )
        }
        //Number of misses
        when (misses) {
            1 -> {
                val p1 = Paint()
                p1.textSize = 100f
                p1.isFakeBoldText = true
                p1.color = Color.RED
                canvas.drawText("X", 50f, 200 + p1.descent() - p1.ascent(), p1)
            }
            2 -> {
                val p1 = Paint()
                p1.textSize = 100f
                p1.isFakeBoldText = true
                p1.color = Color.RED
                canvas.drawText("X X", 50f, 200 + p1.descent() - p1.ascent(), p1)
            }
            3 -> {
                val p1 = Paint()
                p1.textSize = 100f
                p1.isFakeBoldText = true
                p1.color = Color.RED
                canvas.drawText("X X X", 50f, 200 + p1.descent() - p1.ascent(), p1)
            }
        }
    }

    init {
        letters = ArrayList()
        this.letterLocation = letterLocation
        this.playerSize = playerSize
        this.letterHeight = letterHeight
        this.color = color
        initialization = System.currentTimeMillis()
        start = initialization

        //Add letters to the array
        populateLetters()
    }


}

class SliceTheLetterGamePanel(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
    private lateinit var thread: SliceTheLetterThread
    private val user: SliceTheLetterPlayer = TODO()
    private lateinit var userPoint: Point
    private lateinit var letterManager: LetterManager
    private var gameOver = false
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = SliceTheLetterThread(getHolder(), this)
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    //Creates a new letterManager and resets the location of the user
    private fun resetGame() {
        userPoint = Point(150, 150)
        user.update(userPoint)
        letterManager = LetterManager(200, 200, 325, Color.argb(0, 255, 255, 255))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                //Reset the game if ended
                if (gameOver) {
                    resetGame()
                    gameOver = false
                }
            MotionEvent.ACTION_MOVE -> userPoint[event.x.toInt()] = event.y.toInt()
        }
        return true
    }

    fun update() {

        //Game is continuing
        if (!gameOver) {

            //Move the user to the new point
            user.update(userPoint)

            //Update the letterManager
            val x = letterManager.update()

            //Check if three letters have been missed
            if (x) {
                gameOver = true
            }
            val y = letterManager.collisionDetection(user)

            //Check if wrong has been hit
            if (y) {
                gameOver = true
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        //do I actually need to draw it here or it's ok to just have it in fragment
        // (well constrained and matched perfectly)?
        // canvas.drawBitmap("slice_the_letter_background",,Paint())
        user.draw(canvas)
        letterManager.draw(canvas)
        //Set gameOver Animation
        if (gameOver) {
            //play gameOver animation


        }
    }


    init {
        holder.addCallback(this)
        thread = SliceTheLetterThread(holder, this)

        //Instantiate player
        user = SliceTheLetterPlayer(Rect(100, 100, 200, 200), Color.argb(0, 0, 0, 0))

        //Instantiate location of the player
        userPoint = Point(150, 150)
        user.update(userPoint)

        //Instantiate the letter-managing class
        letterManager = LetterManager(200, 200, 325, Color.argb(0, 255, 255, 255))
        isFocusable = true
    }


    class SliceTheLetterThread(
        private val surfaceHolder: SurfaceHolder,
        private val gamePanel: SliceTheLetterGamePanel
    ) :
        Thread() {
        private var avgFPS = 0.0
        private var running = false
        fun setRunning(running: Boolean) {
            this.running = running
        }

        override fun run() {
            var startTime: Long
            var time: Long
            var waitTime: Long
            var frameCount = 0
            var totalTime: Long = 0
            val targetTime = (1000 / maximalFPS).toLong()
            while (running) {
                startTime = System.nanoTime()
                canvas = null
                try {
                    canvas = surfaceHolder.lockCanvas()
                    synchronized(surfaceHolder) {
                        gamePanel.update()
                        gamePanel.draw(canvas)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                time = System.nanoTime() - startTime / 1000000
                waitTime = targetTime - time
                try {
                    if (waitTime > 0) {
                        this.sleep(waitTime)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                totalTime += System.nanoTime() - startTime
                frameCount++
                if (frameCount == maximalFPS) {
                    avgFPS = (1000 / (totalTime / frameCount / 1000000)).toDouble()
                    frameCount = 0
                    totalTime = 0
                    println(avgFPS)
                }
            }
        }

        companion object {
            const val maximalFPS = 30
            var canvas: Canvas? = null
        }
    }
}

    class SliceTheLetterPlayer(val rectangle: Rect, private val color: Int) : GameObject {
        override fun draw(canvas: Canvas?) {
            val paint = Paint()
            paint.color = color
            canvas?.drawRect(rectangle, paint)
        }


        override fun update() {}
        fun update(point: Point) {

            //Set new location of the user
            rectangle[point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2] =
                point.y + rectangle.height() / 2
        }
    }

