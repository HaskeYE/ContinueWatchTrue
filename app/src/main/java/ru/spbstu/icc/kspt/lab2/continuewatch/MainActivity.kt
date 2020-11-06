package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var prefs: SharedPreferences



    override fun onStart() {
        super.onStart()
        if(!backgroundThread.isAlive) backgroundThread.start()
        if(prefs != null){
            secondsElapsed = prefs.getInt("REPLACE WITH AN INTEGER", 0)
        }
    }



    override fun onStop() {
        super.onStop()
        val editor = prefs.edit()
        editor.putInt("REPLACE WITH AN INTEGER", secondsElapsed).apply()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (lifecycle.currentState == Lifecycle.State.RESUMED)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (outState != null) {
            super.onSaveInstanceState(outState)
        }
        // Save the user's current game state
        outState?.run {
            putInt("REPLACE WITH AN INTEGER", secondsElapsed)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs =
            getSharedPreferences("seconds", Context.MODE_PRIVATE)
            backgroundThread.start()
    }
}
