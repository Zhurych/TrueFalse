package com.test.truefalse.di

import com.test.truefalse.view.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    internal abstract fun contributeGameFragment(): GameFragment

    @ContributesAndroidInjector
    internal abstract fun contributeResultFragment(): ResultFragment

    @ContributesAndroidInjector
    internal abstract fun contributeDefeatFragment(): DefeatFragment

    @ContributesAndroidInjector
    internal abstract fun contributeAboutAppFragment(): AboutAppFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSettingsFragment(): SettingsFragment
}