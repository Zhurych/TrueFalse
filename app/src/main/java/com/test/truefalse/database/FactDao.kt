package com.test.truefalse.database

import android.util.Log
import androidx.room.*

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFacts(listFacts: List<Fact>)

    @Query("UPDATE facts SET `order` = :random, isUsed = :isUsed WHERE id = :id")
    suspend fun updateOrder(random: Int, isUsed: Boolean, id: Int): Int

    @Query("UPDATE facts SET isUsed = :isUsed WHERE id = :id")
    suspend fun setIsUsedTrue(isUsed: Boolean, id: Int): Int

    @Query("SELECT * FROM facts WHERE isUsed = :isUsed AND theme IN (:listThemes) ORDER BY `order` LIMIT 30")
    suspend fun getUnusedFacts(isUsed: Boolean, listThemes: List<Int>): MutableList<Fact>

    @Query("SELECT * FROM facts WHERE isUsed = :isUsed AND theme IN (:listThemes) ORDER BY RANDOM() LIMIT :limit")
    suspend fun getUsedFacts(isUsed: Boolean, listThemes: List<Int>, limit: Int): List<Fact>

    @Query("SELECT id FROM facts")
    suspend fun getIdFacts(): List<Int>

    @Transaction
    suspend fun shuffle(listFacts: List<Int>): Int {
        var updatesCount = 0
        for ((index, id) in listFacts.withIndex()) {
            val count = updateOrder(random = id, isUsed = false, id = index)
            updatesCount += count
        }
        Log.d("MyLogs", "Dao. Размер зашавленных данных = $updatesCount")
        return updatesCount
    }
}