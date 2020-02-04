package com.test.truefalse.view

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.readystatesoftware.systembartint.SystemBarTintManager
import com.test.truefalse.R
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, VB : ViewDataBinding> : DaggerAppCompatActivity() {

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

    protected abstract fun afterCreate(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        initStatusBar()
        super.onCreate(savedInstanceState)

        vb = DataBindingUtil.setContentView(this, layout())

        try {
            vm = ViewModelProvider(this, viewModelFactory).get(vmTypeClass)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        afterCreate(savedInstanceState)
    }

    private fun initStatusBar() {
        val window: Window = window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val tintManager = SystemBarTintManager(this)
            tintManager.isStatusBarTintEnabled = true
            tintManager.setTintColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        }
    }
}