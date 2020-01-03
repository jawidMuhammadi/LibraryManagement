package com.example.library.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.preference.PreferenceManager
import com.example.library.R
import com.example.library.adapter.BOOKS_FRAGMENT
import com.example.library.adapter.MainActivityPagerAdaper
import com.example.library.adapter.STUDENT_FRAGMENT
import com.example.library.login.LoginActivity
import com.example.library.login.LoginViewModel.Companion.USER_LOGGED_IN
import com.example.library.register.RegisterActivity
import com.example.library.settings.SettingsActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        tv_title.text = getString(R.string.app_name)


        view_pager.adapter = MainActivityPagerAdaper(this)
        TabLayoutMediator(tab, view_pager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        nav_view.setNavigationItemSelectedListener(this)

        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                USER_LOGGED_IN,
                false
            )
        ) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getTabTitle(position: Int): CharSequence? {
        return when (position) {
            STUDENT_FRAGMENT -> getString(R.string.students)
            BOOKS_FRAGMENT -> getString(R.string.books)
            else -> null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> onSettingMenuClicked()
            R.id.menu_register -> onRegisterMenuClick()
            else -> drawer_layout?.openDrawer(GravityCompat.START)
        }
        return true
    }

    private fun onRegisterMenuClick() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun onSettingMenuClicked() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_register -> {
                onRegisterMenuClick()
                drawer_layout?.closeDrawers()
                true
            }
            else -> false
        }
    }
}
