package com.test.truefalse.view

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.test.truefalse.R
import com.test.truefalse.viewModel.BaseViewModel
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : DaggerFragment() {

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

        setHasOptionsMenu(true)
        afterCreateView(view, savedInstanceState)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_activity, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuNewGame -> {
                vm.newGame(findNavController())
                return true
            }
            R.id.menuSetting -> {
                vm.settings()
                return true
            }
            R.id.menuAboutApp -> {
                vm.aboutApp(findNavController())
                return true
            }
            R.id.menuExit -> {
                vm.exit(activity = activity as AppCompatActivity)
                return true
            }
        }

        return false
    }
}