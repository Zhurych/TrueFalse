package com.test.truefalse.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.core.content.ContextCompat
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

    @BindingAdapter("timer")
    @JvmStatic
    fun setTimer(view: TextView, secondLeft: Int) {
        val context = App.applicationContext()

        view.text = String.format(context.getString(R.string.second_left), secondLeft)
    }

    @BindingAdapter("answer")
    @JvmStatic
    fun setAnswer(view: TextView, answer: Answer) {
        val context = App.applicationContext()

        when {
            answer.userAnswer && answer.factAnswer -> {
                view.text = context.getString(R.string.true_string)
                view.background =
                    ContextCompat.getDrawable(context, R.drawable.correct_answer_background)
            }
            answer.userAnswer && !answer.factAnswer -> {
                view.text = context.getString(R.string.true_string)
                view.background =
                    ContextCompat.getDrawable(context, R.drawable.wrong_answer_background)
            }
            !answer.userAnswer && answer.factAnswer -> {
                view.text = context.getString(R.string.false_string)
                view.background =
                    ContextCompat.getDrawable(context, R.drawable.wrong_answer_background)
            }
            !answer.userAnswer && !answer.factAnswer -> {
                view.text = context.getString(R.string.false_string)
                view.background =
                    ContextCompat.getDrawable(context, R.drawable.correct_answer_background)
            }
        }
    }

    @BindingAdapter("stars")
    @JvmStatic
    fun setStars(view: ImageView, percent: Int) {
        val nightMode = AppCompatDelegate.getDefaultNightMode()

        if (nightMode == MODE_NIGHT_NO) {
            when {
                percent < 7 -> view.setImageResource(R.drawable.ic_stars_0)
                percent < 30 -> view.setImageResource(R.drawable.ic_stars_0_5)
                percent < 47 -> view.setImageResource(R.drawable.ic_stars_1)
                percent < 63 -> view.setImageResource(R.drawable.ic_stars_1_5)
                percent < 80 -> view.setImageResource(R.drawable.ic_stars_2)
                percent < 100 -> view.setImageResource(R.drawable.ic_stars_2_5)
                else -> view.setImageResource(R.drawable.ic_stars_3)
            }
        } else {
            when {
                percent < 7 -> view.setImageResource(R.drawable.ic_stars_0_night)
                percent < 30 -> view.setImageResource(R.drawable.ic_stars_0_5_night)
                percent < 47 -> view.setImageResource(R.drawable.ic_stars_1_night)
                percent < 63 -> view.setImageResource(R.drawable.ic_stars_1_5_night)
                percent < 80 -> view.setImageResource(R.drawable.ic_stars_2_night)
                percent < 100 -> view.setImageResource(R.drawable.ic_stars_2_5_night)
                else -> view.setImageResource(R.drawable.ic_stars_3_night)
            }
        }
    }

    @BindingAdapter("version")
    @JvmStatic
    fun setVersion(view: TextView, version: String) {
        val context = App.applicationContext()

        view.text = String.format(context.getString(R.string.version), version)
    }
}