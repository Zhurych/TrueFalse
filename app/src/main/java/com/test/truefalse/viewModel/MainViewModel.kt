package com.test.truefalse.viewModel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel
@Inject constructor(
) : ViewModel() {
    @Inject
    lateinit var mNumberOfFactsAnswered: ObservableInt
    @Inject
    lateinit var isNeedToHideToolbar: ObservableBoolean
}