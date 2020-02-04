package com.test.truefalse.di

import com.test.truefalse.database.AppDatabase
import com.test.truefalse.model.repository.FactsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    internal fun provideFactsRepository(db: AppDatabase) = FactsRepositoryImpl(db)
}