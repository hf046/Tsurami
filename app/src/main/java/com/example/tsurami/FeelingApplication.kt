package com.example.tsurami

import android.app.Application
import android.util.Log
import timber.log.Timber

class FeelingApplication : Application() {
    companion object {
        const val TAG = "FeelingApplication"
    }

    override fun onCreate() {
        Log.d(TAG, "\\[:onCreate]")
        super.onCreate()
        Log.d(TAG, "\\:configureTimber")
        configureTimber()
        Log.d(TAG, "\\:onCreate end\n;")
    }

    private fun configureTimber() {
        Log.d(TAG, "\\[:configureTimber]")
        Log.d(TAG, "\\:[BuildConfig.DEBUG]:${BuildConfig.DEBUG};")
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "\\:setting Timber")
            Timber.plant(Timber.DebugTree())
        }
        Log.d(TAG, "\\:configureTimer end\n;")
    }
}