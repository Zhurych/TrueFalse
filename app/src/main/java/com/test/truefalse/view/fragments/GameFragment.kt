package com.test.truefalse.view.fragments

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import com.test.truefalse.R
import com.test.truefalse.customClasses.TinderCard
import com.test.truefalse.customClasses.getDisplaySize
import com.test.truefalse.databinding.FragmentGameBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.view.activities.MainActivity
import com.test.truefalse.viewModel.GameViewModel


class GameFragment : BaseFragment<GameViewModel, FragmentGameBinding>(), View.OnClickListener {

    private var isBackToApp = false

    private val mMenuVisibilityListener = object : ActionBar.OnMenuVisibilityListener {
        override fun onMenuVisibilityChanged(isVisible: Boolean) {
            if (isVisible) { // menu expanded
                if (!vm.isPaused.get()) {
                    vm.countDownTimer.pause()
                }
                if (vm.isPlaying.get()) {
                    vm.isPaused.set(true)
                }
                Log.d("MyLogs", "GameFragment. Меню открыто")
            } else { // menu collapsed
                if (vm.isPaused.get()) {
                    vm.countDownTimer.resume()
                    vm.isPaused.set(false)
                }
                Log.d("MyLogs", "GameFragment. Меню закрыто")
            }
        }
    }

    lateinit var actionBar: ActionBar

    override fun layout() = R.layout.fragment_game

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        Log.d("MyLogs", "GameFragment. afterCreateView")

        vb.vm = vm

        vm.mNumberOfFactsAnswered = (activity as MainActivity).getNumberOfFactsAnswered()

        vm.initNavController(findNavController())

        vb.buttonGameFragmentTrue.setOnClickListener(this)
        vb.buttonGameFragmentFalse.setOnClickListener(this)

        actionBar = (activity as AppCompatActivity).supportActionBar!!

        actionBar.addOnMenuVisibilityListener(mMenuVisibilityListener)

        vb.clGameFragmentRoot.post {
            val bottomMargin: Int = vb.guidelineBottom20Percent.bottom
            val windowSize: Point = getDisplaySize(activity!!.windowManager)!!

            vb.swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(2)
                .setSwipeDecor(
                    SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setMarginTop(0)
                        .setRelativeScale(0f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view)
                        .setSwipeOutMsgGravity(Gravity.END)
                        .setSwipeInMsgGravity(Gravity.START)
                )

            vm.liveFacts.observe(this, Observer {
                Log.d("MyLogs", "GameFragment. Подписчик liveFacts it = $it")
                if (!it.isNullOrEmpty() && !vm.isPlaying.get()) {
                    Log.d("MyLogs", "GameFragment. Подписчик liveFacts. Игра не идёт")
                    vb.swipeView.removeAllViews()
                    vm.currentFact.set(vm.liveFacts.value?.get(0))
                    for ((index, fact) in it.withIndex()) {
                        vb.swipeView.addView(
                            TinderCard(
                                mContext = context!!,
                                mFactNumber = index + 1,
                                mFactName = fact.name,
                                container = this
                            )
                        )
                    }
                }
            })

            if (vm.isPlaying.get()) {
                Log.d("MyLogs", "GameFragment. Идёт игра")
                val numberOfFact = vm.mNumberOfFactsAnswered.get() + 1
                val subList = vm.liveFacts.value?.subList(vm.mNumberOfFactsAnswered.get(), 30)
                for ((index, fact) in subList!!.withIndex()) {
                    vb.swipeView.addView(
                        TinderCard(
                            mContext = context!!,
                            mFactNumber = index + numberOfFact,
                            mFactName = fact.name,
                            container = this
                        )
                    )
                }
            } else if (vm.liveFacts.value.isNullOrEmpty()) {
                Log.d("MyLogs", "GameFragment. Не идёт игра и нет фактов")
                vm.getUnusedFacts()
                vm.mNumberOfFactsAnswered.set(0)
                Log.d(
                    "MyLogs",
                    "GameFragment. guidelineBottom20Percent = ${vb.guidelineBottom20Percent.bottom}"
                )
                Log.d(
                    "MyLogs",
                    "GameFragment. guidelineBottom20Percent = ${vb.guidelineBottom20Percent.top}"
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("MyLogs", "GameFragment. onSaveInstanceState")
        super.onSaveInstanceState(outState)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(
            "MyLogs",
            "GameFragment. onViewStateRestored. savedInstanceState = $savedInstanceState"
        )
        super.onViewStateRestored(savedInstanceState)
    }

    fun onSwipe(isTrue: Boolean) {
        Log.d("MyLogs", "GameFragment. onSwipe")
        vm.onSwipe(isTrue)
    }

    override fun onPause() {
        Log.d("MyLogs", "GameFragment. onPause isBackToApp = $isBackToApp")
        super.onPause()
        if (vm.isPlaying.get()) {
            vm.isPaused.set(true)
            vm.countDownTimer.pause()
        }
        isBackToApp = true
    }

    override fun onResume() {
        Log.d("MyLogs", "GameFragment. onResume isBackToApp = $isBackToApp")
        super.onResume()
        if (isBackToApp) {
            vm.isPaused.set(false)
            vm.countDownTimer.resume()
        }
    }

    override fun onDestroyView() {
        Log.d("MyLogs", "GameFragment. onDestroyView")
        super.onDestroyView()
        actionBar.removeOnMenuVisibilityListener(mMenuVisibilityListener)
        isBackToApp = false
    }

    override fun onDestroy() {
        Log.d("MyLogs", "GameFragment. onDestroy")
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonGameFragmentTrue -> vb.swipeView.doSwipe(true)
            else -> vb.swipeView.doSwipe(false)
        }
    }

    fun resumeGame() {
        if (vm.isPaused.get()) {
            vm.isPaused.set(false)
            vm.countDownTimer.resume()
        }
    }
}