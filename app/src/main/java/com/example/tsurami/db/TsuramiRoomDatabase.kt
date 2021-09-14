package com.example.tsurami.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tsurami.db.dao.FeelingSvcDao
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import com.example.tsurami.db.entity.converter.Converter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Database(
    entities = [
        Feeling::class,
        Location::class,
        Comment::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converter::class
)
abstract class TsuramiRoomDatabase : RoomDatabase() {
    abstract fun feelingSvcDao(): FeelingSvcDao

    private class TsuramiDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
//                    TODO(delete all)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TsuramiRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TsuramiRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TsuramiRoomDatabase::class.java,
                    "tsurami_database"
                )
                    .addCallback(TsuramiDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}