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

    @Query("SELECT * FROM facts WHERE isUsed = :isUsed ORDER BY `order` LIMIT 30")
    suspend fun getUnusedFacts(isUsed: Boolean): MutableList<Fact>

    @Query("SELECT * FROM facts WHERE isUsed = :isUsed ORDER BY RANDOM() LIMIT :limit")
    suspend fun getUsedFacts(isUsed: Boolean, limit: Int): List<Fact>

    @Query("SELECT id FROM facts")
    suspend fun getIdFacts(): List<Int>

    @Transaction
    suspend fun shuffle(listFacts: List<Int>): Int {
        var updatesCount = 0
        for ((index, id) in listFacts.withIndex()) {
            val count = updateOrder(random = id, isUsed = false, id = index)
            if (count > 0) Log.d("MyLogs", "Dao. Запись обновлена = $count. Её id = $index. Её order = $id")
            else Log.d("MyLogs", "Dao. Запись не обновлена = $count. Её id = $index. Её order = $id")
            updatesCount += count
        }
        Log.d("MyLogs", "Dao. Размер зашавленных данных = $updatesCount")
        return updatesCount
    }
}