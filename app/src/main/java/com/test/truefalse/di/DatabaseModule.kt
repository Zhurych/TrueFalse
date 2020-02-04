package com.test.truefalse.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.test.truefalse.database.AppDatabase
import com.test.truefalse.database.Fact
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.InputStream
import java.nio.charset.Charset
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
class DatabaseModule {

    lateinit var appDatabase: AppDatabase

    @Singleton
    @Provides
    internal fun provideDatabase(application: Application, listFacts: List<Fact>): AppDatabase {
        appDatabase = Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(IO).launch {
                        appDatabase.factDao().insertFacts(listFacts)
                    }
                }
            }).build()

       // Log.d("MyLogs", "Dagger. provideDatabase. var appDatabase = $appDatabase")
        return appDatabase
    }

    @Singleton
    @Provides
    internal fun provideArrayFact(application: Application): List<Fact> {
        val am = application.assets
        val inputStream: InputStream = am.open("facts.json")

        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        val gson = Gson()

        val result =
            gson.fromJson(String(buffer, Charset.forName("UTF-8")), Array<Fact>::class.java)

        return result.toList()
    }
}