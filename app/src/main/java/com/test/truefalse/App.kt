package com.test.truefalse

import android.app.Activity
import android.app.Application
import android.content.Context
import com.test.truefalse.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .applicationBind(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}