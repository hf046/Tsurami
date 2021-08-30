package com.example.tsurami.repository

import androidx.annotation.WorkerThread
import com.example.tsurami.db.dao.service.TestSvcDao
import com.example.tsurami.db.entity.Test
import kotlinx.coroutines.flow.Flow

class TestRepository(private val testSvcDao: TestSvcDao) {
    val allTests: Flow<List<Test>> = testSvcDao.getTests()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(test: Test) {
        testSvcDao.insert(test)
    }
}