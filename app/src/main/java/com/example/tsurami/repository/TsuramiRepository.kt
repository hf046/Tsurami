package com.example.tsurami.repository

import androidx.annotation.WorkerThread
import com.example.tsurami.db.dao.FeelingSvcDao
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location

class TsuramiRepository(private val feelingSvcDao: FeelingSvcDao) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(feeling: Feeling, location: Location?, comment: Comment?) {
        feelingSvcDao.insert(feeling, location, comment)
    }
}