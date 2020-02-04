package com.test.truefalse.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.truefalse.viewModel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBinder {

    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation
     * */
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(viewModel: MainViewModel): ViewModel
}