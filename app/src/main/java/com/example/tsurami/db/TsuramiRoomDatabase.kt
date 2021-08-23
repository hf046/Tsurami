package com.example.tsurami.db
//
//import android.content.Context
//import androidx.room.*
//import com.example.tsurami.dao.service.AddFeelingDao
//import com.example.tsurami.dao.service.GetFeelingDao
//import com.example.tsurami.entity.Comment
//import com.example.tsurami.entity.Feeling
//import com.example.tsurami.entity.Location
//import com.example.tsurami.entity.converter.Converter
//
//@Database(
//    entities = [
//        Feeling::class,
//        Location::class,
//        Comment::class
//    ],
//    version = 1,
//    exportSchema = false
//)
//@TypeConverters(
//    Converter::class
//)
//public abstract class TsuramiRoomDatabase : RoomDatabase() {
//    abstract fun addFeelingDao(): AddFeelingDao
//    abstract fun getFeelingDao(): GetFeelingDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: TsuramiRoomDatabase? = null
//
//        fun getDatabase(context: Context): TsuramiRoomDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    TsuramiRoomDatabase::class.java,
//                    "tsurami_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}