package com.example.tsurami.db.entity.converter

import androidx.room.TypeConverter
import com.example.tsurami.db.entity.Location
import java.time.ZonedDateTime

class Converter {
    @TypeConverter
    fun convAppDate2DBDate(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value)
    }
    @TypeConverter
    fun convDBDate2AppDate(date: ZonedDateTime): String {
        return date.toString()
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