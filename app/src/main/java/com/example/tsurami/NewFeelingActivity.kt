package com.example.tsurami

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
import com.example.tsurami.entity.Comment
import com.example.tsurami.entity.Feeling
import com.example.tsurami.entity.converter.Converter
import com.google.android.gms.location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NewFeelingActivity : AppCompatActivity() {
    private lateinit var flpc: FusedLocationProviderClient
    var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("\\[:onCreate]")
        super.onCreate(savedInstanceState)

        Timber.d("\\:set layout")
        setContentView(R.layout.activity_new_feeling)

        Timber.d("\\:get elements from layout")
        Timber.d("\\:setup elements")
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
            Timber.d("\\[:<Button> on click]")
            Timber.d("\\:[:location]:$location;")
            Timber.d("\\:create Intent")
            val replyIntent = Intent()
            Timber.d("\\:create Converter")

            val converter = Converter()

            var location4DB: com.example.tsurami.entity.Location? = null
            location?.let{
                Timber.d("\\:convert location")
                location4DB = converter.locApp2DB(it)
            }
            Timber.d("\\:[:location4DB]:$location4DB")
            val date = Date()
            val feeling4DB = Feeling(
                -1,
                location4DB,
                date,
                date,
                mentalParamA.progress,
                mentalParamB.progress
            )
            var comment4DB: Comment? = null
            if (comment.text.isNotEmpty()) {
                comment4DB = Comment(
                    -1,
                    feeling4DB.id,
                    date,
                    date,
                    comment.text.toString()
                )
            }

            Timber.d("\\:store input data in Intent")
            replyIntent.putExtra(EXTRA_REPLY_FEELING, feeling4DB)
            replyIntent.putExtra(EXTRA_REPLY_LOCATION, location4DB)
            replyIntent.putExtra(EXTRA_REPLY_COMMENT, comment4DB)

            Timber.d("\\:set result")
            setResult(Activity.RESULT_OK, replyIntent)
            Timber.d("\\:<Button> on click end\n;")
            finish()
        }

        Timber.d("\\:create FLPC")
        flpc = LocationServices.getFusedLocationProviderClient(this)

        Timber.d("\\:onCreate end\n;")
    }

    @ExperimentalCoroutinesApi
    override fun onStart() {
        Timber.d("\\[:onStart]")
        super.onStart()
        if (!hasPermission(ACCESS_FINE_LOCATION)) {
            Timber.d("\\:require permission")
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), 0)
        }

        Timber.d("\\:get last known location")
        lifecycleScope.launch {
            Timber.d("\\[:get last known location]")
            try {
                Timber.d("\\:update location")
                updateLocation()
            } catch (e: Exception) {
                Timber.d("\\:show fail msg")
                Timber.d("\\:[:e]:$e;")
                val toast = Toast.makeText(
                    this@NewFeelingActivity,
                    "Fail to get location",
                    Toast.LENGTH_LONG
                )
                toast.show()
            } finally {
                Timber.d("\\msg last known location end\n;")
            }
        }
        Timber.d("\\:start updating location")
        startUpdatingLocation()
        Timber.d("\\:onStart end\n;")
    }

    private fun hasPermission(permission: String): Boolean {
        Timber.d("\\[:hasPermission]")
        Timber.d("\\hasPermission end\n;")
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @ExperimentalCoroutinesApi
    private suspend fun updateLocation() {
        Timber.d("\\[:updateLocation]")
        Timber.d("\\:update location")
        location = awaitLastLocation()
        Timber.d("\\:updateLocation end\n;")
    }

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    private suspend fun awaitLastLocation(): Location {
        Timber.d("\\[:awaitLastLocation]")
        val location = suspendCancellableCoroutine<Location> { continuation ->
            Timber.d("\\[:<<crossinline block>>]")
            flpc.lastLocation.addOnSuccessListener { loc ->
                Timber.d("\\[:<Task> on success]")
                Timber.d("\\:[:loc]:$loc;")
                Timber.d("\\:continuation.resume(location)")
                continuation.resume(loc)
                Timber.d("\\:<Task> on success end\n;")
            }.addOnFailureListener { e ->
                Timber.d("\\[:<Task> on failure]")
                Timber.d("\\:[:e]:$e;")
                Timber.d("\\:continuation.resumeWithException(e)")
                continuation.resumeWithException(e)
                Timber.d("\\:<Task> on failure end\n;")
            }
            Timber.d("\\:<<crossinline block>> end\n;")
        }
        Timber.d("\\:awaitLastLocation end\n;")
        return location
    }

    @ExperimentalCoroutinesApi
    private fun startUpdatingLocation() {
        val flow = locationFlow()
        flow.conflate()
            .catch { e ->
                Timber.d("\\[:catch exception]")
                Timber.d("\\:[:e]:$e;")
                Timber.d("\\:make toast")
                val toast = Toast.makeText(
                    this@NewFeelingActivity,
                    "Fail to start updating location",
                    Toast.LENGTH_LONG
                )
                toast.show()
                Timber.d("\\:catch exception end\n;")
            }.asLiveData().observe(this, { loc ->
                Timber.d("\\[:update]")
                Timber.d("\\:update location")
                Timber.d("\\:[:loc]:$loc;")
                location = loc
                Timber.d("\\:update end\n;")
            })
    }

    @SuppressLint("MissingPermission")
    @ExperimentalCoroutinesApi
    private fun locationFlow(): Flow<Location> = callbackFlow<Location> {
        Timber.d("\\[:locationFlow]")
        Timber.d("\\:create callback")
        val callback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                Timber.d("\\[:onLocationResult]")
                Timber.d("\\:has referrer")
                super.onLocationResult(p0)
                if(referrer == null) {
                    Timber.d("\\:has not")
                    Timber.d("\\onLocationResult end\n;")
                    return
                }
                for (location in p0.locations) {
                    Timber.d("\\:send location")
                    val res = trySend(location).isSuccess
                    Timber.d("\\:[:res]:$res;")
                }
                Timber.d("\\:onLocationResult end\n;")
            }
        }

        Timber.d("\\:create request")
        val request = LocationRequest.create().apply {
            interval = 3000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        Timber.d("\\:update FLPC")
        flpc.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            Timber.d("\\[:on fail]")
            Timber.d("\\:close")
            close(e)
            Timber.d("\\:on fail end\n;")
        }

        Timber.d("\\:await close")
        awaitClose {
            Timber.d("\\[:block]")
            flpc.removeLocationUpdates(callback)
            Timber.d("\\:block end\n;")
        }

        Timber.d("\\:locationFlow end\n;")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Timber.d("\\[:onRequestPermissionResult]")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Timber.d("\\:recreate")
            recreate()
        }
        Timber.d("\\:onRequestPermissionResult\n;")
    }

    companion object {
        // enum の方が良いのではないか
        const val EXTRA_REPLY_FEELING = "com.example.tsurami.REPLY_FEELING"
        const val EXTRA_REPLY_LOCATION = "com.example.tsurami.REPLY_LOCATION"
        const val EXTRA_REPLY_COMMENT = "com.example.tsurami.REPLY_COMMENT"
    }
}