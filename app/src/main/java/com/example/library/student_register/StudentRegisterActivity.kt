package com.example.library.student_register

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.library.R
import com.example.library.data.StudentRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_student_register.*

class StudentRegisterActivity : AppCompatActivity() {

    private lateinit var viewModle: StudentRegisterViewModle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)
        setSupportActionBar(toolbar)

        val factory = StudentRegisterViewModleFactory(StudentRepository.getInstance(application))
        viewModle = ViewModelProviders.of(this, factory).get(StudentRegisterViewModle::class.java)

        subscribeUI()
    }

    private fun subscribeUI() {
        supportActionBar?.title = getString(R.string.register_student)
        viewModle.onSaveMenuClick.observe(this, Observer {
            Snackbar.make(
                findViewById(R.id.coordinator_layout),
                getString(R.string.student_registered_succesfully),
                Snackbar.LENGTH_SHORT
            ).show()

            hideSoftInput()
            clearViews()
        })

    }

    private fun clearViews() {
        editTextName.text.clear()
        editTextClass.text.clear()
    }

    private fun hideSoftInput() {
        val input = ContextCompat.getSystemService(
            this,
            InputMethodManager::class.java
        ) as InputMethodManager
        input.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_register_student, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> saveStudent()
            else -> super.onNavigateUp()
        }
        return true
    }

    private fun saveStudent() {
        viewModle.saveStudent(editTextName.text.toString(), editTextClass.text.toString())
        viewModle.onSaveMenuClick()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onNavigateUp()
    }
}
