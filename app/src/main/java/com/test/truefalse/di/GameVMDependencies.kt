package com.test.truefalse.di

import android.util.ArrayMap
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.test.truefalse.database.Fact
import com.test.truefalse.model.Answer
import dagger.Module
import dagger.Provides

@Module
class GameVMDependencies {

    @Provides
    internal fun provideObservableBoolean() = ObservableBoolean(false)

    @Provides
    internal fun provideObservableInt() = ObservableInt(0)

    @Provides
    internal fun provideArrayMap() = ArrayMap<String, Boolean>()

    @Provides
    internal fun provideObservableField() = ObservableField<Fact>()

    @Provides
    internal fun provideLiveData() = MutableLiveData<Boolean>()

    @Provides
    internal fun provideListAnswers() = ArrayList<Answer>()
}