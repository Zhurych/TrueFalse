package com.test.truefalse.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Fact::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
}