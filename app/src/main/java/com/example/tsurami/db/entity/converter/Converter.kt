package com.example.tsurami.db.entity.converter

import androidx.room.TypeConverter
import com.example.tsurami.db.entity.Location
import java.util.Date

class Converter {
//    見本だとnullable型で統一されている。
//    Date
    @TypeConverter
    fun convTimestamp2Date(value: Long): Date {
        return Date(value)
    }
    @TypeConverter
    fun convDate2Timestamp(date: Date): Long {
        return date.time
    }

//    Location
//    @TypeConverter
    fun convAppLoc2ELoc(appLoc: android.location.Location): Location {
        return Location(
            0,
            appLoc.time,
            appLoc.latitude,
            appLoc.longitude,
            if (appLoc.hasAltitude()) appLoc.altitude else null,
            if (appLoc.hasAccuracy()) appLoc.accuracy else null
        )
    }
}