package com.example.library

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.library.settings.ThemType

class LibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val appTheme = PreferenceManager.getDefaultSharedPreferences(this).getString(
            getString(R.string.key_pref_app_theme),
            getString(R.string.pref_default_value)
        )
        when (appTheme) {
            ThemType.DEFAULT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemType.LIGHT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemType.DARK.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        //  scheduleNotificationService(context = this, packageName = packageName)
    }

}