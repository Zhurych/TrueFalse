package com.test.truefalse.view.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.test.truefalse.R
import com.test.truefalse.databinding.FragmentGameBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.GameViewModel

class GameFragment : BaseFragment<GameViewModel, FragmentGameBinding>() {

    private val mMenuVisibilityListener = object : ActionBar.OnMenuVisibilityListener {
        override fun onMenuVisibilityChanged(isVisible: Boolean) {
            if (isVisible) { // menu expanded
                if (vm.isPlaying.get()) vm.isPaused.set(true)
                vm.countDownTimer.pause()
                Log.d("MyLogs", "GameFragment = $this. Меню открыто")
            } else { // menu collapsed
                if (vm.isPaused.get()) {
                    vm.countDownTimer.resume()
                    vm.isPaused.set(false)
                }
                Log.d("MyLogs", "GameFragment = $this. Меню закрыто")
            }
        }
    }

    lateinit var actionBar: ActionBar

    override fun layout() = R.layout.fragment_game

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        Log.d("MyLogs", "GameFragment = $this. afterCreateView")

        vb.vm = vm

        vm.initNavController(findNavController())

        if (savedInstanceState == null) {
            vm.getUnusedFacts()
        }

        // Set scroll on TextView
        vb.tvGameFragmentFact.movementMethod = ScrollingMovementMethod()

        actionBar = (activity as AppCompatActivity).supportActionBar!!

        actionBar.addOnMenuVisibilityListener(mMenuVisibilityListener)
    }

    override fun onDestroyView() {
        Log.d("MyLogs", "GameFragment. onDestroyView")
        super.onDestroyView()
        vm.countDownTimer.cancel()
        actionBar.removeOnMenuVisibilityListener(mMenuVisibilityListener)
    }
}