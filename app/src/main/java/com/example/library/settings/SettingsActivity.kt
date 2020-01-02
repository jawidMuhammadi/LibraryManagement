package com.example.library.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.library.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_layout)
        supportFragmentManager.beginTransaction()
            .replace(R.id.setting_fragment_container, SettingsFragment())
            .commit()
    }
}
