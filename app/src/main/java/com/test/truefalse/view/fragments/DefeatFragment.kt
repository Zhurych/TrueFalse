package com.test.truefalse.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.truefalse.R
import com.test.truefalse.constants.ANSWERED_FACTS_KEY
import com.test.truefalse.databinding.FragmentDefeatBinding
import com.test.truefalse.view.BaseFragment
import com.test.truefalse.viewModel.DefeatViewModel

class DefeatFragment : BaseFragment<DefeatViewModel, FragmentDefeatBinding>() {

    override fun layout() = R.layout.fragment_defeat

    override fun afterCreateView(view: View, savedInstanceState: Bundle?) {
        Log.d("MyLogs", "DefeatFragment. afterCreateView")
        vb.numberOfFactsAnswered = arguments?.getInt(ANSWERED_FACTS_KEY)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MyLogs", "DefeatFragment. onOptionsItemSelected")
        when (item.itemId) {
            R.id.menuNewGame -> {
                findNavController().navigate(R.id.gameFragment)
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
}