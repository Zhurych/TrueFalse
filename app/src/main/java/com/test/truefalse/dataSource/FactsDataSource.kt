package com.test.truefalse.dataSource

import android.util.Log
import com.test.truefalse.database.Fact
import com.test.truefalse.database.FactDao

class FactsDataSource(private val dao: FactDao) {

    suspend fun loadRange(listThemes: List<Int>): List<Fact> {
        var result: List<Fact>
        result = dao.getUnusedFacts(isUsed = false, listThemes = listThemes)
        when {
            // If there are no unused facts
            result.isEmpty() -> {
                Log.d("MyLogs", "DataSource. Закончились факты. Шафлим и тянем заного.")
                var listFactsWithId = dao.getIdFacts()
                Log.d("MyLogs", "DataSource. Размер всех фактов = ${listFactsWithId.size}")
                listFactsWithId = listFactsWithId.shuffled()
                dao.shuffle(listFactsWithId)
                result = dao.getUnusedFacts(isUsed = false, listThemes = listThemes)
            }
            result.size < 30 -> {
                Log.d(
                    "MyLogs",
                    "DataSource. Неиспользованных фактов меньше 30. Тянем использованные факты."
                )
                val listUsedFacts =
                    dao.getUsedFacts(
                        isUsed = true,
                        limit = 30 - result.size,
                        listThemes = listThemes
                    )
                Log.d(
                    "MyLogs",
                    "DataSource. Размер использованных фактов = ${listUsedFacts.size}"
                )
                result.addAll(listUsedFacts)
            }
            else -> {
                return result
            }
        }

        Log.d("MyLogs", "DataSource. Размер листа = ${result.size}")
        return result
    }

    suspend fun updateIsUsed(id: Int) = dao.setIsUsedTrue(isUsed = true, id = id)
}