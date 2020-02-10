package com.test.truefalse.di

import com.test.truefalse.dataSource.FactsDataSource
import com.test.truefalse.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    internal fun provideFactsDataSource(appDatabase: AppDatabase): FactsDataSource {
        return FactsDataSource(appDatabase.factDao())
    }
}