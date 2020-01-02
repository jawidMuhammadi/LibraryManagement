package com.example.library.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.library.R
import com.example.library.login.LoginActivity
import com.example.library.login.LoginViewModel.Companion.USER_LOGGED_IN

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<ListPreference>(getString(R.string.key_pref_app_theme))?.onPreferenceChangeListener =
            this

        findPreference<Preference>(getString(R.string.key_pref_logout))?.setOnPreferenceClickListener {
            logoutUser()
            true
        }
        setupPreferenceSummary()
    }

    private fun setupPreferenceSummary() {
        findPreference<ListPreference>(getString(R.string.key_pref_app_theme))?.apply {
            summaryProvider = Preference.SummaryProvider<ListPreference> {
                val text = it?.value
                if (text.isNullOrEmpty()) {
                    "Not set"
                } else
                    "Your app them is: $text"
            }
        }
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        when (preference?.key) {
            getString(R.string.key_pref_app_theme) -> setAppTheme(newValue)
        }
        return true
    }

    private fun logoutUser() {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putBoolean(USER_LOGGED_IN, false)
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAppTheme(newValue: Any?) {
        when (newValue) {
            ThemType.DEFAULT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemType.LIGHT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemType.DARK.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        requireActivity().recreate()
    }
}