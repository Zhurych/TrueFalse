package com.test.truefalse.model.repository

import com.test.truefalse.database.AppDatabase
import com.test.truefalse.database.Fact
import javax.inject.Inject

class FactsRepositoryImpl
@Inject constructor(
        private val db: AppDatabase
) : FactsRepository {

        override suspend fun getFacts(): List<Fact>? = db.factDao().getFacts()
}
