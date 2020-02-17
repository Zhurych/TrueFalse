package com.test.truefalse

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import com.test.truefalse.constants.DARK
import com.test.truefalse.constants.LIGHT
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

        // This will read your preference.xml file and set the default values defined there.
        // Setting the readAgain argument to false means this will only set the default values if this method
        // has never been called in the past so you don't need to worry about overriding the user's settings
        // each time your Activity is created.
        PreferenceManager.setDefaultValues(this, R.xml.fragment_preferences, false)

        val sharedPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        when (sharedPrefs.getString("theme", null)?.toInt()) {
            LIGHT -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}