package com.test.truefalse.di

import android.util.ArrayMap
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.test.truefalse.database.Fact
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class MainVMDependencies {

    @Provides
    internal fun provideObservableBoolean() = ObservableBoolean(false)

    @Provides
    internal fun provideArrayDeque() = ArrayDeque<Int>((0..252).shuffled())

    @Provides
    internal fun provideObservableInt() = ObservableInt(0)

    @Provides
    internal fun provideArrayMap() = ArrayMap<String, Boolean>()

    @Provides
    internal fun provideObservableField() = ObservableField<Fact>()
}