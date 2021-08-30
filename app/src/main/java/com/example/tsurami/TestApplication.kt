package com.example.tsurami

import android.app.Application
import android.util.Log
import com.example.tsurami.db.TestRoomDatabase
import com.example.tsurami.repository.TestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class TestApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { TestRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TestRepository(database.testSvcDao()) }

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

    companion object {
        const val TAG = "TestApplication"
    }
}