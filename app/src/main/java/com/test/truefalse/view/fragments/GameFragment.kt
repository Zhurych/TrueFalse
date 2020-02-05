package com.test.truefalse.view.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.test.truefalse.R
import com.test.truefalse.databinding.FragmentGameBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.GameViewModel

class GameFragment : BaseFragment<GameViewModel, FragmentGameBinding>() {

    override fun layout() = R.layout.fragment_game

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        Log.d("MyLogs", "GameFragment. afterCreateView")
        Log.d(
            "MyLogs",
            "GameFragment. количество неиспользованных фактов = ${vm.idUnusedFacts.size}"
        )

        vb.vm = vm

        // Set scroll on TextView
        vb.tvGameFragmentFact.movementMethod = ScrollingMovementMethod()

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.addOnMenuVisibilityListener { isVisible ->
            if (isVisible) { // menu expanded
                vm.isPause = true
                vm.countDownTimer.pause()
                Log.d("MyLogs", "Меню открыто")
            } else { // menu collapsed
                if (vm.isPause) vm.countDownTimer.resume()
                else vm.countDownTimer.start()
                vm.isPause = false
                Log.d("MyLogs", "Меню закрыто")
            }
        }

        vm.liveFacts.observe(this, Observer {
            vm.currentFact.set(it?.get(vm.idUnusedFacts.pollLast() ?: return@Observer))
        })
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MyLogs", "GameFragment. onOptionsItemSelected")
        when (item.itemId) {
            R.id.menuNewGame -> {
                vm.countDownTimer.cancel()
                vm.numberOfFactsAnswered.set(0)
                if (vm.idUnusedFacts.isNotEmpty()) {
                    vm.currentFact.set(vm.liveFacts.value?.get(vm.idUnusedFacts.pollLast()))
                    vm.isPlaying.set(false)
                    Log.d("MyLogs", "конец реализации кнопки Новая игра")
                } else {
                    TODO("РЕАЛИЗОВАТЬ ЛОГИКУ, КОГДА ЗАКОНЧИЛИСЬ ФАКТЫ")
                }
                return true
            }
            R.id.menuSetting -> {
                return true
            }
            R.id.menuAboutApp -> {
                return true
            }
            R.id.menuExit -> {
                activity?.finish()
                return true
            }
        }

        return false
    }

    override fun onDestroyView() {
        Log.d("MyLogs", "GameFragment. onDestroyView")
        super.onDestroyView()
        vm.countDownTimer.cancel()
    }
}