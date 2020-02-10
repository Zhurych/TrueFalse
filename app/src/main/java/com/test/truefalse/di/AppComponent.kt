package com.test.truefalse.di

import android.app.Application
import com.test.truefalse.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        ViewModelBinder::class,
        DatabaseModule::class,
        GameVMDependencies::class,
        DataSourceModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationBind(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}