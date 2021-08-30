package com.example.tsurami.db.dao.service

import androidx.room.*
import com.example.tsurami.datum.FeelingDatum
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface FeelingSvcDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(feeling: Feeling)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(feeling: Feeling, location: Location?, comment: Comment?)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun add(feelingDatum: FeelingDatum)

    @Transaction
    @Query(
//        "SELECT " +
//                "feeling.create_date AS date, " +
//                "location.latitude AS latitude, " +
//                "location.longitude AS longitude, " +
//                "feeling.mental_param_a AS a, " +
//                "feeling.mental_param_b AS b, " +
//                "comment.content AS comment " +
//                "FROM feeling " +
//                "LEFT JOIN location " +
//                "ON feeling.location_id = location_id " +
//                "LEFT JOIN comment " +
//                "ON feeling.id = comment.feeling_id " +
//                "ORDER BY feeling.create_date DESC;"
        "SELECT * FROM feeling"
    )
//    https://developer.android.com/codelabs/android-room-with-a-view-kotlin?hl=ja#6
//    データの変更を監視するために、kotlinx-coroutines の Flow を使用
    fun getFeelingData(): Flow<List<FeelingDatum>>

//    TODO(del)
//    TODO(mod)
}