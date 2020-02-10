package com.test.truefalse.view.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.test.truefalse.R
import com.test.truefalse.databinding.FragmentGameBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.GameViewModel

class GameFragment : BaseFragment<GameViewModel, FragmentGameBinding>() {

    override fun layout() = R.layout.fragment_game

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        Log.d("MyLogs", "GameFragment. afterCreateView")

        vb.vm = vm

        vm.initNavController(findNavController())

        if (savedInstanceState == null) {
            vm.getUnusedFacts()
        }

        // Set scroll on TextView
        vb.tvGameFragmentFact.movementMethod = ScrollingMovementMethod()

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.addOnMenuVisibilityListener { isVisible ->
            if (isVisible) { // menu expanded
                vm.isPaused.set(true)
                vm.countDownTimer.pause()
                Log.d("MyLogs", "Меню открыто")
            } else { // menu collapsed
                if (vm.isPaused.get()) vm.countDownTimer.resume()
                else vm.countDownTimer.start()
                vm.isPaused.set(false)
                Log.d("MyLogs", "Меню закрыто")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MyLogs", "GameFragment. onOptionsItemSelected")
        when (item.itemId) {
            R.id.menuNewGame -> {
                vm.newGame()
                Log.d("MyLogs", "конец реализации кнопки Новая игра")
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