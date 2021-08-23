package com.example.tsurami.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

//    location data
    val time: Long, // milliseconds since epoch (January 1, 1970)
    val latitude: Double,
    val longitude: Double,
    val altitude: Double?, // the altitude in meters above the WGS 84 reference ellipsoid.
    val accuracy: Float? // horizontal accuracy of this location, radial, in meters.
): Serializable