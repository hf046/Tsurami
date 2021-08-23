package com.example.tsurami.dao.service

import androidx.room.Dao
import androidx.room.Query
import com.example.tsurami.entity.Feeling
import kotlinx.coroutines.flow.Flow

@Dao
interface GetFeelingDao {
    @Query(
        "SELECT " +
                "feeling.create_date AS date, " +
                "location.latitude AS latitude, " +
                "location.longitude AS longitude, " +
                "feeling.mental_param_a AS a, " +
                "feeling.mental_param_b AS b " +
                "comment.content as comment " +
                "FROM feeling " +
                "LEFT JOIN location " +
                "ON feeling.location_id = location_id " +
                "LEFT JOIN comment " +
                "ON feeling.id = comment.feeling_id " +
                "ORDER BY feeling.create_date DESC;"
    )
//    https://developer.android.com/codelabs/android-room-with-a-view-kotlin?hl=ja#6
//    データの変更を監視するために、kotlinx-coroutines の Flow を使用
    fun get(): Flow<List<Feeling>>
}