package com.test.truefalse.viewModel

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.test.truefalse.model.Answer
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class ResultViewModel
@Inject constructor(

) : ViewModel() {

    @Inject
    lateinit var correctAnswers: ObservableInt
    @Inject
    lateinit var wrongAnswers: ObservableInt
    @Inject
    lateinit var percentCorrectAnswers: ObservableInt

    fun calculatesCorrectAnswers(listAnswers: ArrayList<Answer>) {
        var correctCount = 0
        var wrongCount = 0

        for ((fact, isTrueFact, isTrueAnswer) in listAnswers) {
            when (isTrueAnswer) {
                isTrueFact -> correctCount++
                else -> wrongCount++
            }
        }

        val percent = (correctCount * 100.0 / (correctCount + wrongCount)).roundToInt()
        percentCorrectAnswers.set(percent)
        Log.d("MyLogs", "ResultViewModel. КОЛИЧЕСТВО ПРОЦЕНТОВ ВЕРНЫХ ОТВЕТОВ = ${percentCorrectAnswers.get()}")

        correctAnswers.set(correctCount)
        wrongAnswers.set(wrongCount)
    }
}