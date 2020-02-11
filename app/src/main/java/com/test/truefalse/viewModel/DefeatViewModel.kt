package com.test.truefalse.viewModel

import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.test.truefalse.R
import javax.inject.Inject

class DefeatViewModel
@Inject constructor(

) : BaseViewModel() {

    fun onButtonClick(view: View) {
        view.findNavController().navigate(R.id.gameFragment)
        Log.d("MyLogs", "DefeatViewModel. onButtonClick")
    }

    override fun newGame(navController: NavController) {
        navController.navigate(R.id.gameFragment)
    }

    override fun aboutApp(navController: NavController) {
        navController.navigate(R.id.aboutAppFragment)
    }
}