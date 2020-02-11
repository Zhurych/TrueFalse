package com.test.truefalse.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.truefalse.viewModel.*
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
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    internal abstract fun gameViewModel(viewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    internal abstract fun resultViewModel(viewModel: ResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DefeatViewModel::class)
    internal abstract fun defeatViewModel(viewModel: DefeatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutAppViewModel::class)
    internal abstract fun aboutAppViewModel(viewModel: AboutAppViewModel): ViewModel
}