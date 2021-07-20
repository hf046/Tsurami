package com.example.tsurami

import android.app.Application
import android.util.Log
import timber.log.Timber

class FeelingApplication : Application() {
    companion object {
        const val TAG = "TsuramiApplication"
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate()")
        super.onCreate()
        configureTimber()
    }

    private fun configureTimber() {
        Log.d(TAG, "configureTimber()")
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}