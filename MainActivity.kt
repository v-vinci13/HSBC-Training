package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.CountDownTimer
import android.util.Log
//import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
private  var score = 0

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
        private lateinit var gameScoreTextView: TextView
        private lateinit var timeLeftTextView: TextView
        private lateinit var tapMeButton: Button
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountDown: Long = 60000
    private var countDownInterval: Long = 1000
    private var timeLeft = 60
    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }
        override fun onCreate(savedInstanceState: Bundle?) {

                super.onCreate(savedInstanceState)
            if (savedInstanceState != null) {
                score = savedInstanceState.getInt(SCORE_KEY)
                timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
                restoreGame()
            } else {
                resetGame()
            }


                setContentView(R.layout.activity_main)
                Log.d(TAG, "onceate called. Score os:$score")

            gameScoreTextView = findViewById(R.id.game_score_text_view)
            timeLeftTextView = findViewById(R.id.Time_Left_Text_view)
            tapMeButton = findViewById(R.id.Tab_me_button)
            tapMeButton.setOnClickListener { incrementScore()}
                // connect views to variables
        }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()
        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time Left:$timeLeft")
    }
    // 3
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called.")
    }
    private var gameStarted =false


    private fun incrementScore() {
            // increment score logic
            score++
            val newScore = getString(R.string.your_score,score)
            gameScoreTextView.text = newScore
        }
        private fun resetGame() {
            // reset game logic
            score = 0
            val initialScore = getString(R.string.your_score, score)
            gameScoreTextView.text = initialScore
            val initialTimeLeft = getString(R.string.time_left,60)
            timeLeftTextView.text = initialTimeLeft
            // 2
            countDownTimer = object : CountDownTimer(initialCountDown,
                countDownInterval) {
                // 3
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = millisUntilFinished.toInt()
                    val timeLeftString = getString(R.string.time_left, timeLeft)
                    timeLeftTextView.text = timeLeftString
                }
                override fun onFinish() {

                    endGame()
                    }   }
                gameStarted = false
        }
    private fun restoreGame() {
        val restoredScore = getString(R.string.your_score, score)
        gameScoreTextView.text = restoredScore

        val restoredTime = getString(R.string.time_left, timeLeft)
        timeLeftTextView.text = restoredTime
        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(),
            countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000
                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }
            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }
        private fun startGame() {
            // start game logic
        }
        private fun endGame() {
            Toast.makeText(this, getString(R.string.game_over_message, score),
                Toast.LENGTH_LONG).show()
            resetGame()
        }
    }


