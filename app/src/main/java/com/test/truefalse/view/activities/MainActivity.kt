package com.test.truefalse.view.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.test.truefalse.R
import com.test.truefalse.constants.*
import com.test.truefalse.databinding.ActivityMainBinding
import com.test.truefalse.view.BaseActivity
import com.test.truefalse.viewModel.MainViewModel


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    lateinit var mNavHostFragment: NavHostFragment

    lateinit var mNavController: NavController

    override fun layout() = R.layout.activity_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        title = ""
        setSupportActionBar(vb.tbMainActivity)

        vb.vm = vm

        mNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        mNavController = findNavController(R.id.nav_host_fragment_container)

        mNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.label) {
                GAME_FRAGMENT_LABEL -> {
                    vm.isNeedToHideToolbar.set(false)
                }
                RESULT_FRAGMENT_LABEL -> {
                    vm.isNeedToHideToolbar.set(false)
                }
                DEFEAT_FRAGMENT_LABEL -> {
                    vm.isNeedToHideToolbar.set(false)
                }
                SETTINGS_FRAGMENT_LABEL -> {
                    vm.isNeedToHideToolbar.set(true)
                    title = getString(R.string.settings)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                ABOUT_APP_FRAGMENT_LABEL -> {
                    vm.isNeedToHideToolbar.set(true)
                }
            }
        }

        Log.d("MyLogs", "MainActivity mNumberOfFactsAnswered = ${vm.mNumberOfFactsAnswered}")
        Log.d("MyLogs", "MainActivity. game = $mNavHostFragment")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d("MyLogs", "MainActivity. onSaveInstanceState")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("MyLogs", "MainActivity. onSupportNavigateUp")
        mNavController.navigateUp()
        return true
    }

    fun getNumberOfFactsAnswered() = vm.mNumberOfFactsAnswered
}