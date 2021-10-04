package com.example.tsurami.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "location"
)
data class Location(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

//    location data
    @ColumnInfo(name = "time")
    val time: Long?, // milliseconds since epoch (January 1, 1970)
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "altitude")
    val altitude: Double?, // the altitude in meters above the WGS 84 reference ellipsoid.
    @ColumnInfo(name = "accuracy")
    val accuracy: Float? // horizontal accuracy of this location, radial, in meters.
): Serializable