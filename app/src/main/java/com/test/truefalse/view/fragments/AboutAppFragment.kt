package com.test.truefalse.view.fragments

import android.os.Bundle
import android.view.View
import com.test.truefalse.BuildConfig
import com.test.truefalse.R
import com.test.truefalse.databinding.FragmentAboutAppBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.AboutAppViewModel

class AboutAppFragment : BaseFragment<AboutAppViewModel, FragmentAboutAppBinding>() {
    override fun layout() = R.layout.fragment_about_app

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        activity?.title = getString(R.string.about_app)
        val versionName = BuildConfig.VERSION_NAME
        vb.version = versionName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.title = ""
    }
}