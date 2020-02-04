package com.test.truefalse.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VB : ViewDataBinding, VM : ViewModel> : DaggerFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var vb: VB
    protected lateinit var vm: VM

    private val vmTypeClass: Class<VM>
        get() {
            try {
                val className =
                    (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0].toString()
                val clazz = Class.forName(className.replace("class ", ""))
                @Suppress("UNCHECKED_CAST")
                return clazz as Class<VM>
            } catch (e: Exception) {
                throw IllegalStateException(e.message)
            }
        }

    @LayoutRes
    protected abstract fun layout(): Int

    protected abstract fun afterCreateView(view: View, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout(), container, false)

        vb = DataBindingUtil.bind(view)!!

        try {
            vm = ViewModelProvider(this, viewModelFactory).get(vmTypeClass)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        afterCreateView(view, savedInstanceState)
        return view
    }
}