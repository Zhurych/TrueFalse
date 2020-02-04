package com.test.truefalse.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.test.truefalse.App
import com.test.truefalse.R

object BindingAdapter {

    @BindingAdapter("factsAnswered")
    @JvmStatic
    fun setAnsweredFacts(view: TextView, factsAnswered: Int) {
        val context = App.applicationContext()

        view.text =
            String.format(context.getString(R.string.number_of_facts_answered), factsAnswered)
    }

    @BindingAdapter("factNumber")
    @JvmStatic
    fun setFactNumber(view: TextView, factsAnswered: Int) {
        val context = App.applicationContext()

        view.text = String.format(context.getString(R.string.fact_number), factsAnswered + 1)
    }

    @BindingAdapter("timer")
    @JvmStatic
    fun setTimer(view: TextView, secondLeft: Int) {
        val context = App.applicationContext()

        view.text = String.format(context.getString(R.string.second_left), secondLeft)
    }
}