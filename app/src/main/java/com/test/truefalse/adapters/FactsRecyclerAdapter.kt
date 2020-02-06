package com.test.truefalse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.truefalse.R
import com.test.truefalse.databinding.ResultFactItemBinding
import com.test.truefalse.model.Answer

class FactsRecyclerAdapter(private val listAnswers: ArrayList<Answer>) :
    RecyclerView.Adapter<FactsRecyclerAdapter.ResultOfFactHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultOfFactHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ResultFactItemBinding>(
            inflater,
            R.layout.result_fact_item,
            parent,
            false
        )

        return ResultOfFactHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultOfFactHolder, position: Int) {
        val currentAnswer = listAnswers[position]

        holder.bind(currentAnswer)
    }

    override fun getItemCount() = listAnswers.size

    inner class ResultOfFactHolder(private val binding: ResultFactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(answer: Answer) {
            binding.answer = answer

            binding.executePendingBindings()
        }
    }
}