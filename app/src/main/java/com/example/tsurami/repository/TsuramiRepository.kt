package com.example.tsurami.repository

import androidx.annotation.WorkerThread
import com.example.tsurami.db.dao.service.FeelingSvcDao
import com.example.tsurami.datum.FeelingDatum
import com.example.tsurami.db.entity.Feeling
import kotlinx.coroutines.flow.Flow

class TsuramiRepository(private val feelingSvcDao: FeelingSvcDao) {
    val allFeelings: Flow<List<FeelingDatum>> = feelingSvcDao.getFeelingData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(feeling: Feeling) {
        feelingSvcDao.insert(feeling)
//        feelingSvcDao.add(feelingDatum)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(feelingDatum: FeelingDatum) {
        feelingSvcDao.insert(feelingDatum.feeling, feelingDatum.location, feelingDatum.comment)
//        feelingSvcDao.add(feelingDatum)
    }
}