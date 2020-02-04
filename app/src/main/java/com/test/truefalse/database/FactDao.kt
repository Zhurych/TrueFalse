package com.test.truefalse.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFacts(listFacts: List<Fact>): List<Long>

    @Query("SELECT * FROM facts")
    suspend fun getFacts(): List<Fact>?
}