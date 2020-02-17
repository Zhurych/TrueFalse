package com.test.truefalse.viewModel

import androidx.navigation.NavController
import com.test.truefalse.R
import javax.inject.Inject

class SettingsViewModel
@Inject constructor(

) : BaseViewModel() {
    override fun newGame(navController: NavController) {
        navController.navigate(R.id.gameFragment)
    }

    override fun settings(navController: NavController) {

    }

    override fun aboutApp(navController: NavController) {
        navController.navigate(R.id.aboutAppFragment)
    }

}