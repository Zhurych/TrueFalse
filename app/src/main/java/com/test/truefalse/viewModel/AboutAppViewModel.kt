package com.test.truefalse.viewModel

import androidx.navigation.NavController
import com.test.truefalse.R
import javax.inject.Inject

class AboutAppViewModel
@Inject constructor(

) : BaseViewModel() {

    override fun newGame(navController: NavController) {
        navController.navigate(R.id.gameFragment)
    }

    override fun aboutApp(navController: NavController) {}
}