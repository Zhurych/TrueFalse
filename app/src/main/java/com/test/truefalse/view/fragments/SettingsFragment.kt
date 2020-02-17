package com.test.truefalse.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.test.truefalse.R
import com.test.truefalse.view.activities.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.fragment_preferences, rootKey)

        activity?.title = getString(R.string.settings)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listOfThemes = findPreference<ListPreference>("theme")

        listOfThemes?.setOnPreferenceClickListener {
            Log.d("MyLogs", "Нажал на тему")
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.title = ""
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}