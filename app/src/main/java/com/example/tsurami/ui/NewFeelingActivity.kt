package com.example.tsurami.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.SeekBar
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.tsurami.R
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.converter.Converter
import com.google.android.gms.location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NewFeelingActivity : AppCompatActivity() {
    private lateinit var flpc: FusedLocationProviderClient
    var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_feeling)

        val mentalParamA = findViewById<SeekBar>(R.id.mental_param_a)
        mentalParamA.max = 1000
        mentalParamA.min = 0
        mentalParamA.progress = 500

        val mentalParamB = findViewById<SeekBar>(R.id.mental_param_b)
        mentalParamB.max = 1000
        mentalParamB.min = 0
        mentalParamB.progress = 500

        val comment = findViewById<EditText>(R.id.comment)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener() {
            val replyIntent = Intent()

            val converter = Converter()

            var location4DB: com.example.tsurami.db.entity.Location? = null
            location?.let{
                location4DB = converter.convAppLoc2ELoc(it)
            }

            val date = Date()
            val feeling4DB = Feeling(
                0,
                location4DB?.id,
                date,
                date,
                mentalParamA.progress,
                mentalParamB.progress
            )

            var comment4DB: Comment? = null
            if (comment.text.isNotEmpty()) {
                comment4DB = Comment(
                    0,
                    feeling4DB.id,
                    date,
                    date,
                    comment.text.toString()
                )
            }

            replyIntent.putExtra(EXTRA_REPLY_FEELING, feeling4DB)
            replyIntent.putExtra(EXTRA_REPLY_LOCATION, location4DB)
            replyIntent.putExtra(EXTRA_REPLY_COMMENT, comment4DB)

            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

        flpc = LocationServices.getFusedLocationProviderClient(this)
    }

    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()
        if (!hasPermission(ACCESS_FINE_LOCATION)) {
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), 0)
        }

        lifecycleScope.launch {
            try {
                updateLocation()
            } catch (e: Exception) {
                val toast = Toast.makeText(
                    this@NewFeelingActivity,
                    "Fail to get location",
                    Toast.LENGTH_LONG
                )
                toast.show()
            } finally {
            }
        }
        startUpdatingLocation()
    }

    private fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @ExperimentalCoroutinesApi
    private suspend fun updateLocation() {
        location = awaitLastLocation()
    }

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    private suspend fun awaitLastLocation(): Location {
        val location = suspendCancellableCoroutine<Location> { continuation ->
            flpc.lastLocation.addOnSuccessListener { loc ->
                continuation.resume(loc)
            }.addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
        }
        return location
    }

    @ExperimentalCoroutinesApi
    private fun startUpdatingLocation() {
        val flow = locationFlow()
        flow.conflate()
            .catch { e ->
                val toast = Toast.makeText(
                    this@NewFeelingActivity,
                    "Fail to start updating location",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }.asLiveData().observe(this, { loc ->
                location = loc
            })
    }

    @SuppressLint("MissingPermission")
    @ExperimentalCoroutinesApi
    private fun locationFlow(): Flow<Location> = callbackFlow<Location> {
        val callback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                if(referrer == null) {
                    return
                }
//                for (location in p0.locations) {
//                    val res = trySend(location).isSuccess
//                }
            }
        }

        val request = LocationRequest.create().apply {
            interval = 3000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        flpc.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e)
        }

        awaitClose {
            flpc.removeLocationUpdates(callback)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate()
        }
    }

    companion object {
        // enum の方が良いのではないか
        const val EXTRA_REPLY_FEELING = "com.example.tsurami.REPLY_FEELING"
        const val EXTRA_REPLY_LOCATION = "com.example.tsurami.REPLY_LOCATION"
        const val EXTRA_REPLY_COMMENT = "com.example.tsurami.REPLY_COMMENT"
    }
}