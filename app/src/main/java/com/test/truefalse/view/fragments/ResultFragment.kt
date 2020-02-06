package com.test.truefalse.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.truefalse.R
import com.test.truefalse.adapters.FactsRecyclerAdapter
import com.test.truefalse.constants.LIST_ANSWERS_KEY
import com.test.truefalse.databinding.FragmentResultBinding
import com.test.truefalse.model.Answer
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.ResultViewModel


class ResultFragment : BaseFragment<ResultViewModel, FragmentResultBinding>() {

    override fun layout() = R.layout.fragment_result

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        vb.vm = vm

        val recyclerView = vb.rvResultFragment
        recyclerView.layoutManager = LinearLayoutManager(context)

        val listAnswers = arguments?.getSerializable(LIST_ANSWERS_KEY) as ArrayList<Answer>
        Log.d("MyLogs", "ResultFragment. СПИСОК С ОТВЕТАМИ = ${listAnswers.size}")

        vm.calculatesCorrectAnswers(listAnswers)
        recyclerView.adapter = FactsRecyclerAdapter(listAnswers)
    }
}