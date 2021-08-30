package com.example.tsurami.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tsurami.db.dao.service.TestSvcDao
import com.example.tsurami.db.entity.Test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Test::class],
    version = 1,
    exportSchema = false
)
abstract class TestRoomDatabase : RoomDatabase() {
    abstract fun testSvcDao(): TestSvcDao

    private class TestDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var testSvcDao = database.testSvcDao()

                    testSvcDao.deleteAll()

                    var test = Test(0, "Hello")
                    testSvcDao.insert(test)
                    test = Test(0, "World")
                    testSvcDao.insert(test)
                    test = Test(0, "test")
                    testSvcDao.insert(test)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TestRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TestRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestRoomDatabase::class.java,
                    "test_database"
                )
                    .addCallback(TestDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}