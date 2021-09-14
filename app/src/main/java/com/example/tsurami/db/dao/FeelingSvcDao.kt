package com.example.tsurami.db.dao

import androidx.room.*
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import com.example.tsurami.db.entity.Comment

@Dao
interface FeelingSvcDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(feeling: Feeling): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(location: Location): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(comment: Comment): Long

    @Transaction
    suspend fun insert(feeling: Feeling, location: Location?, comment: Comment?) {
        var locationId : Int? = null
        if (location != null) {
            locationId = insert(location).toInt()
        }

        feeling.locationId = locationId
        val feelingId = insert(feeling)

        if (comment != null) {
            comment.feelingId = feelingId.toInt()
            insert(comment)
        }
    }
}