package com.example.tsurami.db.dao.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tsurami.db.entity.Test
import kotlinx.coroutines.flow.Flow

@Dao
interface TestSvcDao {
    @Query("SELECT * FROM test ORDER BY test.id ASC")
    fun getTests(): Flow<List<Test>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(test: Test)

    @Query("DELETE FROM test")
    suspend fun deleteAll()
}