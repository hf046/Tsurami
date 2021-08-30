package com.example.tsurami.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tsurami.db.entity.Feeling

@Dao
interface FeelingDao {
    @Query(
        "SELECT " +
                "feeling.create_date AS date, " +
                "location.latitude AS latitude, " +
                "location.longitude AS longitude, " +
                "feeling.mental_param_a AS a, " +
                "feeling.mental_param_b AS b " +
                "FROM feeling " +
                "LEFT JOIN location " +
                "ON feeling.location_id = location_id " +
                "ORDER BY feeling.create_date DESC;"
    )
    fun getAllFeelings(): List<Feeling>

    @Query(
        "select " +
                "feeling.create_date as date, " +
                "location.latitude as latitude, " +
                "location.longitude as longitude, " +
                "feeling.mental_param_a as a, " +
                "feeling.mental_param_b as b " +
                "from feeling " +
                "left join location on feeling.location_id = location.id " +
                "where feeling.create_date > :date_from and feeling.create_date < :date_to " +
                "order by feeling.create_date desc;"
    )
    fun getFeelings(date_from: Long, date_to:Long): List<Feeling>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(feeling: Feeling): Int
}