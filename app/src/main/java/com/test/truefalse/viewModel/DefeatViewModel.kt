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
        view.findNavController().navigate(R.id.action_defeatFragment_to_gameFragment)
        Log.d("MyLogs", "DefeatViewModel. onButtonClick")
    }

    override fun newGame(navController: NavController) {
        navController.navigate(R.id.action_defeatFragment_to_gameFragment)
    }

    override fun settings(navController: NavController) {
        navController.navigate(R.id.action_defeatFragment_to_settingsFragment)
    }

    override fun aboutApp(navController: NavController) {
        navController.navigate(R.id.action_defeatFragment_to_aboutAppFragment)
    }
}