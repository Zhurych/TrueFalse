package com.test.truefalse.view.activities

import android.os.Bundle
import com.test.truefalse.R
import com.test.truefalse.databinding.ActivityMainBinding
import com.test.truefalse.view.BaseActivity
import com.test.truefalse.viewModel.MainViewModel


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun layout() = R.layout.activity_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        title = ""
        // Log.d("MyLogs", "afterCreate")
        setSupportActionBar(vb.tbMainActivity)
    }
}