package com.example.tsurami

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class NewFeelingActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REPLY_DATETIME = "com.example.tsurami.REPLY_DATETIME"
        const val EXTRA_REPLY_LOCATION = "com.example.tsurami.REPLY_LOCATION"
        const val EXTRA_REPLY_SANITY = "com.example.tsurami.REPLY_SANITY"
        const val EXTRA_REPLY_ACTIVENESS = "com.example.tsurami.REPLY_ACTIVENESS"
        const val EXTRA_REPLY_DESCRIPTION = "com.example.tsurami.REPLY_DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feeling)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener() {
            Log.d("test", "button onClick()")
            val replyIntent = Intent()
            Log.d("test", "[${replyIntent.hashCode()}] ${replyIntent}")
            replyIntent.putExtra(EXTRA_REPLY_DATETIME, "<datetime>")
            replyIntent.putExtra(EXTRA_REPLY_SANITY, "<sanity>")
            replyIntent.putExtra(EXTRA_REPLY_ACTIVENESS, "<activeness>")
            replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION, "<description>")
            replyIntent.putExtra(EXTRA_REPLY_LOCATION, "<location>")
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }
}