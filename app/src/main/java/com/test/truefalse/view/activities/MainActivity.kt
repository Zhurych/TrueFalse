package com.test.truefalse.view.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.test.truefalse.R
import com.test.truefalse.databinding.ActivityMainBinding
import com.test.truefalse.view.BaseActivity
import com.test.truefalse.viewModel.MainViewModel


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun layout() = R.layout.activity_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        // Log.d("MyLogs", "afterCreate")
        setSupportActionBar(vb.tbMainActivity)
        supportActionBar?.addOnMenuVisibilityListener { isVisible ->
            if (isVisible) { // menu expanded
                vm.countDownTimer.pause()
            } else { // menu collapsed
                vm.countDownTimer.resume()
            }
        }
        title = ""
        vb.vm = vm


        vm.liveFacts.observe(this, Observer {
            //            Log.d("MyLogs", "MainActivity. afterCreate. Подписчик liveFacts = $it")
//            Log.d("MyLogs", "MainActivity. afterCreate. Подписчик liveFacts. РАЗМЕР СПИСКА = ${it?.size}")
            vm.currentFact.set(it?.get(vm.idUnusedFacts.pollLast() ?: return@Observer))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MyLogs", "onOptionsItemSelected")
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.countDownTimer.cancel()
    }
}