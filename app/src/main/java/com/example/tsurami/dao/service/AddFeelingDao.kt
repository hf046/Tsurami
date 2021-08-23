package com.example.tsurami.dao.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.tsurami.entity.Comment
import com.example.tsurami.entity.Feeling
import com.example.tsurami.entity.Location

@Dao
interface AddFeelingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(feeling: Feeling, location: Location?, comment: Comment?)
}