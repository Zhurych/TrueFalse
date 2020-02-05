package com.test.truefalse.view.fragments

import android.os.Bundle
import android.view.View
import com.test.truefalse.R
import com.test.truefalse.databinding.FragmentResultBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.ResultViewModel

class ResultFragment : BaseFragment<ResultViewModel, FragmentResultBinding>() {

    override fun layout() = R.layout.fragment_result

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {

    }
}