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

    private val mSwipeView: SwipePlaceHolderView by lazy {
        if (vm.isPaused.get()) {
            vm.mSwipeView
        } else {
            vm.mSwipeView = vb.swipeView as SwipePlaceHolderView
            vb.swipeView as SwipePlaceHolderView
        }
    }

    private val mMenuVisibilityListener = object : ActionBar.OnMenuVisibilityListener {
        override fun onMenuVisibilityChanged(isVisible: Boolean) {
            if (isVisible) { // menu expanded
                if (vm.isPlaying.get()) vm.isPaused.set(true)
                vm.countDownTimer.pause()
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

        vm.initNavController(findNavController())

        vm.mNumberOfFactsAnswered = (activity as MainActivity).getNumberOfFactsAnswered()

        if (savedInstanceState == null) {
            Log.d("MyLogs", "GameFragment. savedInstanceState = $savedInstanceState")
            vm.getUnusedFacts()
        }

        vb.buttonGameFragmentTrue.setOnClickListener(this)
        vb.buttonGameFragmentFalse.setOnClickListener(this)

        actionBar = (activity as AppCompatActivity).supportActionBar!!

        actionBar.addOnMenuVisibilityListener(mMenuVisibilityListener)

        vb.clGameFragmentRoot.post {
            if (vm.isPaused.get()) {
                vm.isPaused.set(false)
                vm.countDownTimer.resume()
                Log.d("MyLogs", "GameFragment. mSwipeView = $mSwipeView")
            } else {
                vm.mNumberOfFactsAnswered.set(0)
                val bottomMargin: Int = vb.guidelineBottom20Percent.bottom
                Log.d(
                    "MyLogs",
                    "GameFragment. guidelineBottom20Percent = ${vb.guidelineBottom20Percent.bottom}"
                )
                Log.d(
                    "MyLogs",
                    "GameFragment. guidelineBottom20Percent = ${vb.guidelineBottom20Percent.top}"
                )
                Log.d(
                    "MyLogs",
                    "GameFragment. guidelineBottom20Percent = ${vb.guidelineBottom20Percent.left}"
                )
                Log.d(
                    "MyLogs",
                    "GameFragment. guidelineBottom20Percent = ${vb.guidelineBottom20Percent.right}"
                )
                val windowSize: Point = getDisplaySize(activity!!.windowManager)!!

                val builder =
                    mSwipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
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

                Log.d("MyLogs", "GameFragment. builder = $builder")

                vm.liveFacts.observe(this, Observer {
                    Log.d("MyLogs", "GameFragment. Подписчик liveFacts it = $it")
                    if (!it.isNullOrEmpty()) {
                        Log.d(
                            "MyLogs",
                            "GameFragment. Подписчик liveFacts. mSwipeView = $mSwipeView"
                        )
                        mSwipeView.removeAllViews()
                        vm.currentFact.set(vm.liveFacts.value?.get(0))
                        for ((index, fact) in it.withIndex()) {
                            mSwipeView.addView(
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

    override fun onDestroyView() {
        Log.d("MyLogs", "GameFragment. onDestroyView")
        super.onDestroyView()
        vm.countDownTimer.pause()
        actionBar.removeOnMenuVisibilityListener(mMenuVisibilityListener)
        vm.isPaused.set(true)
    }

    override fun onDestroy() {
        Log.d("MyLogs", "GameFragment. onDestroy")
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonGameFragmentTrue -> mSwipeView.doSwipe(true)
            else -> mSwipeView.doSwipe(false)
        }
    }
}