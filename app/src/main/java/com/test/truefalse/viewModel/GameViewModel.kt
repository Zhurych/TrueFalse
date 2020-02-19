package com.test.truefalse.viewModel

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.test.truefalse.App
import com.test.truefalse.R
import com.test.truefalse.constants.*
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
) : BaseViewModel() {
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
    @Inject
    lateinit var liveFacts: MutableLiveData<List<Fact>>

    lateinit var mNumberOfFactsAnswered: ObservableInt

    lateinit var mNavController: NavController
    lateinit var mListOfTopics: List<Int>
    var mDifficulty: Int = 1

    init {
        Log.d("MyLogs", "GameViewModel. init")
        getPreferences()
    }

    // The first parameter is the number of milliseconds from which the count will be made
    // The second parameter is the number of milliseconds that are the callback interval (onTick () method)
    var countDownTimer = getTimer()

    fun onSwipe(isTrue: Boolean) {
        Log.d("MyLogs", "GameViewModel. onSwipe")
        countDownTimer.cancel()
        val oldValue = mNumberOfFactsAnswered.get()
        val id = currentFact.get()!!.id

        viewModelScope.launch(IO) {
            val idUpdate = dataSource.updateIsUsed(id)
            Log.d("MyLogs", "GameViewModel. onSwipe. ФАКТ ОБНОВЛЁН. ЕГО ID = $idUpdate")
        }

        Log.d(
            "MyLogs",
            "GameViewModel. onSwipe. Проверка на количество отвеченных фактов = $oldValue"
        )
        if (oldValue <= 28) {
            isPlaying.set(true)
            countDownTimer.start()
            mNumberOfFactsAnswered.set(oldValue + 1)
            putGameResult(isTrue)
            Log.d(
                "MyLogs",
                "GameViewModel. onSwipe. numberOfFactsAnswered = ${mNumberOfFactsAnswered.get()}"
            )
            currentFact.set(liveFacts.value?.get(mNumberOfFactsAnswered.get()))

        } else {
            mNumberOfFactsAnswered.set(oldValue + 1)
            isPlaying.set(false)
            putGameResult(isTrue)
            val bundle = Bundle()
            bundle.putSerializable(LIST_ANSWERS_KEY, gameResult)
            mNavController.navigate(R.id.action_gameFragment_to_resultFragment, bundle)
        }
    }

    private fun putGameResult(isTrue: Boolean) {
        val currentFact = currentFact.get() ?: return

        val answer = Answer(currentFact.name, currentFact.isTrue, isTrue)
        gameResult.add(answer)
    }

    fun getUnusedFacts() {
        Log.d("MyLogs", "GameViewModel. getUnusedFacts")
        Log.d("MyLogs", "GameViewModel. mListOfTopics = $mListOfTopics")

        viewModelScope.launch(IO) {
            liveFacts.postValue(dataSource.loadRange(mListOfTopics).shuffled())
        }
    }

    override fun newGame(navController: NavController) {
        Log.d("MyLogs", "GameViewModel. New Game")
        if (isPlaying.get()) countDownTimer.cancel()
        isPlaying.set(false)
        getPreferences()
        getUnusedFacts()
        mNumberOfFactsAnswered.set(0)
        countDownTimer = getTimer()
    }

    override fun settings(navController: NavController) {
        navController.navigate(R.id.action_gameFragment_to_settingsFragment)
    }

    override fun aboutApp(navController: NavController) {
        navController.navigate(R.id.aboutAppFragment)
    }

    fun initNavController(navController: NavController) {
        mNavController = navController
    }

    private fun getPreferences() {
        val sharedPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.applicationContext())
        val selectedTopics: MutableSet<String>? = sharedPrefs.getStringSet("themes", null)

        Log.d("MyLogs", "GameViewModel. init. selectedTopics = $selectedTopics")

        mDifficulty = sharedPrefs.getString("difficulty", null)?.toInt()!!
        Log.d("MyLogs", "GameViewModel. init. difficulty = $mDifficulty")
        mListOfTopics = selectedTopics!!.map { it.toInt() }
    }

    private fun getTimer(): CountDownTimer {
        return object : CountDownTimer(
            when (mDifficulty) {
                1 -> EASY
                2 -> MEDIUM
                else -> HARD
            }, 1_000
        ) {
            // Called when the timer reaches zero
            override fun onFinish() {
                isPlaying.set(false)
                Log.d("MyLogs", "GameViewModel. mNavController = ${mNavController.currentDestination}")
                mNavController.navigate(R.id.action_gameFragment_to_defeatFragment)
                Log.d("MyLogs", "GameViewModel. CountDownTimer. onFinish")
            }

            override fun onTick(millisUntilFinished: Long) {
                secondsLeft.set((millisUntilFinished / 1000).toInt())
                Log.d("MyLogs", "GameViewModel. CountDownTimer. onTick")
            }
        }
    }
}