package com.example.tsurami.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(
    tableName = "feeling",
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
//    ,
//    indices = [
//        Index(
//            value = ["location_id"],
//            unique = true
//        )
//    ]
)
data class Feeling(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
//    location
//    val location_id: Int?,
    val location: Location?,

//    dates
    val create_date: Date,
    val update_date: Date,
//    mental_params
    val mental_param_a: Int,
    val mental_param_b: Int
): Serializable