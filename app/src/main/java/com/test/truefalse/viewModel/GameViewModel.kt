package com.test.truefalse.viewModel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.test.truefalse.R
import com.test.truefalse.constants.ANSWERED_FACTS_KEY
import com.test.truefalse.constants.HARD
import com.test.truefalse.constants.LIST_ANSWERS_KEY
import com.test.truefalse.customClasses.CountDownTimer
import com.test.truefalse.dataSource.FactsDataSource
import com.test.truefalse.database.Fact
import com.test.truefalse.model.Answer
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class GameViewModel
@Inject constructor(
    private val dataSource: FactsDataSource
) : ViewModel() {

    @Inject
    lateinit var numberOfFactsAnswered: ObservableInt
    @Inject
    lateinit var currentFact: ObservableField<Fact>
    @Inject
    lateinit var isPlaying: ObservableBoolean
    @Inject
    lateinit var secondsLeft: ObservableInt
    @Inject
    lateinit var gameResult: ArrayList<Answer>
    @Inject
    lateinit var isPaused: ObservableBoolean

    lateinit var listFacts: List<Fact>

    lateinit var mNavController: NavController

    // The first parameter is the number of milliseconds from which the count will be made
    // The second parameter is the number of milliseconds that are the callback interval (onTick () method)
    val countDownTimer = object : CountDownTimer(HARD, 1_000) {
        // Called when the timer reaches zero
        override fun onFinish() {
            val bundle = Bundle()
            bundle.putInt(ANSWERED_FACTS_KEY, numberOfFactsAnswered.get())
            mNavController.navigate(R.id.defeatFragment, bundle)
            Log.d("MyLogs", "GameViewModel. CountDownTimer. onFinish")
        }

        override fun onTick(millisUntilFinished: Long) {
            secondsLeft.set((millisUntilFinished / 1000).toInt())
            Log.d("MyLogs", "GameViewModel. CountDownTimer. onTick")
        }
    }

    fun onButtonTrueClick(view: View) {
        countDownTimer.cancel()
        val oldValue = numberOfFactsAnswered.get()
        val id = currentFact.get()!!.id

        viewModelScope.launch(IO) {
            val idUpdate = dataSource.updateIsUsed(id)
            Log.d("MyLogs", "GameViewModel. onButtonTrueClick. ФАКТ ОБНОВЛЁН. ЕГО ID = $idUpdate")
        }

        if (oldValue <= 28) {
            isPlaying.set(true)
            countDownTimer.start()
            numberOfFactsAnswered.set(oldValue + 1)
            putGameResult(view)
            Log.d(
                "MyLogs",
                "GameViewModel. onButtonTrueClick. numberOfFactsAnswered = ${numberOfFactsAnswered.get()}"
            )
            currentFact.set(listFacts[numberOfFactsAnswered.get()])

        } else {
            isPlaying.set(false)
            putGameResult(view)
            val bundle = Bundle()
            bundle.putSerializable(LIST_ANSWERS_KEY, gameResult)
            view.findNavController().navigate(R.id.resultFragment, bundle)
        }
        Log.d("MyLogs", "GameViewModel. onButtonTrueClick")
    }

    private fun putGameResult(view: View) {
        val currentFact = currentFact.get() ?: return

        val answerResult: Boolean = when (view.id) {
            R.id.buttonGameFragmentTrue -> {
                true
            }
            else -> false
        }

        val answer = Answer(currentFact.name, currentFact.isTrue, answerResult)
        gameResult.add(answer)
    }

    fun getUnusedFacts() {
        Log.d("MyLogs", "GameViewModel. getUnusedFacts")
        viewModelScope.launch(IO) {
            listFacts = dataSource.loadRange().shuffled()
            Log.d("MyLogs", "GameViewModel. Вызов dataSource.loadRange(). Размер результата = ${listFacts.size}")
            currentFact.set(listFacts[0])
        }
    }

    fun newGame() {
        Log.d("MyLogs", "GameViewModel. New Game")
        getUnusedFacts()
        countDownTimer.cancel()
        numberOfFactsAnswered.set(0)
        isPlaying.set(false)
    }

    fun initNavController(navController: NavController) {
        mNavController = navController
    }
}