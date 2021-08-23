package com.example.tsurami.entity.converter

import androidx.room.TypeConverter
import com.example.tsurami.entity.Location
import java.util.Date

class Converter {
//    Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    @TypeConverter
    fun date2Timestamp(date: Date?): Long? {
        return date?.time
    }

//    Location
    @TypeConverter
    fun locApp2DB(appLoc: android.location.Location?): Location? {
        if (appLoc == null) return null
        return Location(
            -1,
            appLoc.time,
            appLoc.latitude,
            appLoc.longitude,
            if (appLoc.hasAltitude()) appLoc.altitude else null,
            if (appLoc.hasAccuracy()) appLoc.accuracy else null
        )
    }
//    @TypeConverter
//    fun locTimeApp2DB(location: android.location.Location): Long {
//        return location.time
//    }
//    @TypeConverter
//    fun locLatApp2DB(location: android.location.Location): Double {
//        return location.latitude
//    }
//    @TypeConverter
//    fun locLngApp2DB(location: android.location.Location): Double {
//        return location.longitude
//    }
//    @TypeConverter
//    fun locAltApp2DB(location: android.location.Location): Double? {
//        if (location.hasAltitude()) {
//            return location.altitude
//        }
//        return null
//    }
//    @TypeConverter
//    fun locAccuracyApp2DB(location: android.location.Location): Float? {
//        if (location.hasAccuracy()) {
//            return location.accuracy
//        }
//        return null
//    }
}