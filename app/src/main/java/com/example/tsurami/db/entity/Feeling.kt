package com.example.tsurami.db.entity

import androidx.room.*
import java.io.Serializable
import java.time.ZonedDateTime

@Entity(
    tableName = "feeling",
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(
            value = ["location_id"],
            unique = true
        )
    ]
)
data class Feeling(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
//    location
    @ColumnInfo(name = "location_id")
    var locationId: Int?,

//    dates
    @ColumnInfo(name = "create_date")
    val createDate: ZonedDateTime,
    @ColumnInfo(name = "update_date")
    val updateDate: ZonedDateTime,
//    mental_params
    @ColumnInfo(name = "mental_param_a")
    val mentalParamA: Int,
    @ColumnInfo(name = "mental_param_b")
    val mentalParamB: Int
): Serializable