package com.example.tsurami

import android.app.Application
import android.util.Log
import com.example.tsurami.db.TsuramiRoomDatabase
import com.example.tsurami.repository.TsuramiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class TsuramiApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { TsuramiRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TsuramiRepository(database.feelingSvcDao()) }
}