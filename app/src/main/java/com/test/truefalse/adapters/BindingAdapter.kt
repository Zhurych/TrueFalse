package com.test.truefalse.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.test.truefalse.App
import com.test.truefalse.R
import com.test.truefalse.model.Answer

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

    @BindingAdapter("answer")
    @JvmStatic
    fun setAnswer(view: ImageView, answer: Answer) {
        if (answer.isTrueAnswer && answer.isTrueFact) view.setImageResource(R.drawable.ic_true_green)
        else if (answer.isTrueAnswer && !answer.isTrueFact) view.setImageResource(R.drawable.ic_true_red)
        else if (!answer.isTrueAnswer && answer.isTrueFact) view.setImageResource(R.drawable.ic_false_red)
        else if (!answer.isTrueAnswer && !answer.isTrueFact) view.setImageResource(R.drawable.ic_false_green)
    }

    @BindingAdapter("correct")
    @JvmStatic
    fun setCorrect(view: TextView, correctCount: Int) {
        val context = App.applicationContext()

        view.text = String.format(context.getString(R.string.correct_answer), correctCount)
    }

    @BindingAdapter("wrong")
    @JvmStatic
    fun setWrong(view: TextView, wrongCount: Int) {
        val context = App.applicationContext()

        view.text = String.format(context.getString(R.string.wrong_answer), wrongCount)
    }

    @BindingAdapter("stars")
    @JvmStatic
    fun setStars(view: ImageView, percent: Int) {
        when {
            percent < 7 -> view.setImageResource(R.drawable.ic_stars_0_white)
            percent < 30 -> view.setImageResource(R.drawable.ic_stars_0_5_white)
            percent < 47 -> view.setImageResource(R.drawable.ic_stars_1_white)
            percent < 63 -> view.setImageResource(R.drawable.ic_stars_1_5_white)
            percent < 80 -> view.setImageResource(R.drawable.ic_stars_2_white)
            percent < 100 -> view.setImageResource(R.drawable.ic_stars_2_5_white)
            else -> view.setImageResource(R.drawable.ic_stars_3_white)
        }
    }
}