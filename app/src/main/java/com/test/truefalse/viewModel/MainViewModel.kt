package com.test.truefalse.viewModel

import android.util.ArrayMap
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.test.truefalse.R
import com.test.truefalse.constants.HARD
import com.test.truefalse.customClasses.CountDownTimer
import com.test.truefalse.database.Fact
import com.test.truefalse.model.repository.FactsRepositoryImpl
import java.util.*
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val repository: FactsRepositoryImpl
) : ViewModel() {

    @Inject
    lateinit var isPlaying: ObservableBoolean
    @Inject
    lateinit var idUnusedFacts: ArrayDeque<Int>
    @Inject
    lateinit var numberOfFactsAnswered: ObservableInt
    @Inject
    lateinit var gameResult: ArrayMap<String, Boolean>
    @Inject
    lateinit var currentFact: ObservableField<Fact>
    @Inject
    lateinit var secondsLeft: ObservableInt

    val countDownTimer = object : CountDownTimer(HARD, 1_000) {
        // Called when the timer reaches zero
        override fun onFinish() {
            // TODO: РЕАЛИЗОВАТЬ ЛОГИКУ ПРОИГРЫША
            Log.d("MyLogs", "MainViewModel. CountDownTimer. onFinish")
        }

        override fun onTick(millisUntilFinished: Long) {
            secondsLeft.set((millisUntilFinished / 1000).toInt())
            Log.d("MyLogs", "MainViewModel. CountDownTimer. onTick")
        }
    }

    val liveFacts = liveData {
        emit(repository.getFacts())
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun onButtonTrueClick(view: View) {
        countDownTimer.cancel()
        val oldValue = numberOfFactsAnswered.get()
        if (oldValue <= 29) {
            val isExistFacts = idUnusedFacts.isNotEmpty()
            if (isExistFacts) {
                isPlaying.set(true)
                // The first parameter is the number of milliseconds from which the count will be made
                // The second parameter is the number of milliseconds that are the callback interval (onTick () method)
                countDownTimer.start()
                numberOfFactsAnswered.set(oldValue + 1)
                putGameResult(view)
                currentFact.set(liveFacts.value?.get(idUnusedFacts.pollLast()))
            } else {
                TODO("РЕАЛИЗОВАТЬ ЛОГИКУ, КОГДА ЗАКОНЧИЛИСЬ ФАКТЫ")
            }
        } else {
            isPlaying.set(false)
            TODO("РЕАЛИЗОВАТЬ: КОНЕЦ ИГРЫ С ПОКАЗОМ РЕЗУЛЬТАТОВ")
        }
        Log.d("MyLogs", "MainViewModel. onButtonTrueClick")
    }

    private fun putGameResult(view: View) {
        val answerResult: Boolean
        val isTrue = currentFact.get()?.isTrue
        answerResult = if (isTrue!! && view.id == R.id.buttonMainActivityTrue) {
            true
        } else if (isTrue && view.id == R.id.buttonMainActivityFalse) {
            false
        } else !(!isTrue && view.id == R.id.buttonMainActivityTrue)

        gameResult[currentFact.get()?.name] = answerResult
    }
}