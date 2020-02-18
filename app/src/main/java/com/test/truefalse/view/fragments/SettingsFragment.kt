package com.test.truefalse.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.test.truefalse.R
import com.test.truefalse.view.activities.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        Log.d("MyLogs", "SettingsFragment. onCreatePreferences")
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.fragment_preferences, rootKey)

        activity?.title = getString(R.string.settings)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.d("MyLogs", "SettingsFragment. onCreatePreferences. supportActionBar = ${(activity as MainActivity).supportActionBar}")
        Log.d("MyLogs", "SettingsFragment. onCreatePreferences. Установка стрелки назад")

        val listOfThemes = findPreference<ListPreference>("theme")

        listOfThemes?.setOnPreferenceChangeListener { preference, newValue ->
            Log.d("MyLogs", "Тема поменялась")
            Log.d("MyLogs", "preference = $preference")
            Log.d("MyLogs", "newValue = $newValue")

            when (newValue) {
                "1" -> {
                    Log.d("MyLogs", "Выбрана светлая тема")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                "2" -> {
                    Log.d("MyLogs", "Выбрана тёмная тема")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        Log.d("MyLogs", "SettingsFragment. onDestroyView")
        super.onDestroyView()
        activity?.title = ""
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroy() {
        Log.d("MyLogs", "SettingsFragment. onDestroy")
        super.onDestroy()
    }
}