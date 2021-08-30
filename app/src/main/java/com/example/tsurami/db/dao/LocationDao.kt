package com.example.tsurami.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.tsurami.db.entity.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: Location): Int
}