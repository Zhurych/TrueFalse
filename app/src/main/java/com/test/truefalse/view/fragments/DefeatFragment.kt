package com.test.truefalse.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.test.truefalse.R
import com.test.truefalse.constants.ANSWERED_FACTS_KEY
import com.test.truefalse.databinding.FragmentDefeatBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.DefeatViewModel

class DefeatFragment : BaseFragment<DefeatViewModel, FragmentDefeatBinding>() {

    override fun layout() = R.layout.fragment_defeat

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        Log.d("MyLogs", "DefeatFragment. afterCreateView")
        vb.vm = vm
    }
}