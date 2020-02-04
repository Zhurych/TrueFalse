package com.test.truefalse.model.repository

import com.test.truefalse.database.Fact

interface FactsRepository {
    suspend fun getFacts(): List<Fact>?
}