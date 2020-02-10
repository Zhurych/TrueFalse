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
import java.util.*
import javax.inject.Singleton


@Module
class DatabaseModule {

    lateinit var appDatabase: AppDatabase

    @Singleton
    @Provides
    internal fun provideDatabase(application: Application): AppDatabase {
        appDatabase = Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    Log.d("MyLogs", "Callback при создании базыю onCreate")
                    super.onCreate(db)

                    val am = application.assets
                    val inputStream: InputStream = am.open("facts.json")

                    val buffer = ByteArray(inputStream.available())
                    inputStream.read(buffer)
                    inputStream.close()

                    val gson = Gson()

                    val listFacts =
                        gson.fromJson(String(buffer, Charset.forName("UTF-8")), Array<Fact>::class.java)

                    val listOrder = ArrayList<Int>((listFacts.indices).shuffled())

                    for ((index, random) in listOrder.withIndex()) {
                        Log.d("MyLogs", "создание базы. index = $index")
                        listFacts[index].order = random
                    }

                    Log.d("MyLogs", "создание базы. Размер = ${listFacts.size}")

                    CoroutineScope(IO).launch {
                        appDatabase.factDao().insertFacts(listFacts.toList())
                    }
                }
            }).build()

       // Log.d("MyLogs", "Dagger. provideDatabase. var appDatabase = $appDatabase")
        return appDatabase
    }
}