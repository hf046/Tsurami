package com.example.tsurami

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.EditText
import android.widget.Button
import timber.log.Timber
import java.util.Date

class NewFeelingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_feeling)

        val sanity = findViewById<SeekBar>(R.id.sanity)
        sanity.max = 1000
        sanity.min = 0
        sanity.progress = 500

        val activeness = findViewById<SeekBar>(R.id.activeness)
        activeness.max = 1000
        activeness.min = 0
        activeness.progress = 500

        val description = findViewById<EditText>(R.id.description)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener() {
            Timber.d("onClick()")
            Timber.d("[:description.text]:${description.text};")

            val replyIntent = Intent()
            Timber.d("[:replyIntent.hashCode:replyIntent]:${replyIntent.hashCode()}:$replyIntent;")

            replyIntent.putExtra(EXTRA_REPLY_DATETIME, Date().time)
            replyIntent.putExtra(EXTRA_REPLY_SANITY, sanity.progress)
            replyIntent.putExtra(EXTRA_REPLY_ACTIVENESS, activeness.progress)
            replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION, description.text.toString())
            replyIntent.putExtra(EXTRA_REPLY_LOCATION, "<location>")

            setResult(Activity.RESULT_OK, replyIntent)
            Timber.d("[:description.text]:${description.text};")
            finish()
        }
    }

    companion object {
        // enum の方が良いのではないか
        const val EXTRA_REPLY_DATETIME = "com.example.tsurami.REPLY_DATETIME"
        const val EXTRA_REPLY_LOCATION = "com.example.tsurami.REPLY_LOCATION"
        const val EXTRA_REPLY_SANITY = "com.example.tsurami.REPLY_SANITY"
        const val EXTRA_REPLY_ACTIVENESS = "com.example.tsurami.REPLY_ACTIVENESS"
        const val EXTRA_REPLY_DESCRIPTION = "com.example.tsurami.REPLY_DESCRIPTION"
    }
}