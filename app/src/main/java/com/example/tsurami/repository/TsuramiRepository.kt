package com.example.tsurami.repository

import androidx.annotation.WorkerThread
import com.example.tsurami.db.dao.FeelingSvcDao
import com.example.tsurami.db.datum.FeelingDatum
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import kotlinx.coroutines.flow.Flow

class TsuramiRepository(private val feelingSvcDao: FeelingSvcDao) {
    val allWords: Flow<List<FeelingDatum>> = feelingSvcDao.getAllFeelingData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(feeling: Feeling, location: Location?, comments: List<Comment>) {
        feelingSvcDao.insert(feeling, location, comments)
    }
}