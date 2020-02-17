package com.test.truefalse.customClasses

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.*
import com.test.truefalse.R
import com.test.truefalse.view.fragments.GameFragment


@Layout(R.layout.tinder_card_view)
class TinderCard(
    private val mContext: Context,
    private val mFactNumber: Int,
    private val mFactName: String,
    private val container: GameFragment
) {
    @View(R.id.tvFactNumber)
    private val factNumber: TextView? = null
    @View(R.id.tvFactName)
    private val factName: TextView? = null

    @Resolve
    private fun onResolved() {
        factNumber?.text =
            String.format(mContext.getString(R.string.fact_number), mFactNumber)
        factName?.text = mFactName
    }

    @SwipeOut
    private fun onSwipedOut() {
        Log.d("MyLogs", "onSwipedOut")
        container.onSwipe(false)
    }

    @SwipeCancelState
    private fun onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState")
    }

    @SwipeIn
    private fun onSwipeIn() {
        Log.d("MyLogs", "onSwipedIn")
        container.onSwipe(true)
    }

    @SwipeInState
    private fun onSwipeInState() {
        Log.d("EVENT", "onSwipeInState")
    }

    @SwipeOutState
    private fun onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState")
    }
}