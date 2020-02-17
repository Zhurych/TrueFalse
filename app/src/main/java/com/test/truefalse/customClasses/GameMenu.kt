package com.test.truefalse.customClasses

import androidx.navigation.NavController

interface GameMenu {

    fun newGame(navController: NavController) {}

    fun settings(navController: NavController) {}

    fun aboutApp(navController: NavController) {}
}