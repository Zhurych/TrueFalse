package com.test.truefalse.viewModel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

abstract class BaseViewModel : ViewModel() {

    abstract fun newGame(navController: NavController)

    fun settings() {}

    abstract fun aboutApp(navController: NavController)

    fun exit(activity: AppCompatActivity) {
        activity.finish()
    }
}