package com.example.library.book_register

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.library.R
import com.example.library.book_register.BookRegisterViewModel.Companion.simpleDateFormat
import com.example.library.data.BookRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_book_resgister.*
import kotlinx.android.synthetic.main.activity_book_resgister.toolbar
import kotlinx.android.synthetic.main.activity_student_register.*
import java.util.*

class BookRegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: BookRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_resgister)
        setSupportActionBar(toolbar)

        val factory = BookRegisterViewModelFactory(BookRepository.getInstance(application))
        viewModel = ViewModelProviders.of(this, factory).get(BookRegisterViewModel::class.java)

        val adapter = ArrayAdapter<String>(
            this, R.layout.support_simple_spinner_dropdown_item
        )

        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: ArrayAdapter<String>) {
        supportActionBar?.title = "${simpleDateFormat.format(viewModel.borrowedDate.timeInMillis)}"
        sp_students_name.onItemSelectedListener = this
        sp_students_name.adapter = adapter

        viewModel.studentsName.observe(this, Observer {
            adapter.addAll(it)
        })

        viewModel.onSaveMenuClick.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Snackbar.make(
                    findViewById(R.id.coordinator_layout),
                    getString(R.string.student_registered_succesfully),
                    Snackbar.LENGTH_SHORT
                ).show()

                hideSofKeyboard()
                cleanViews()
            }
        })
    }

    private fun cleanViews() {
        et_book_name.text.clear()
        ed_book_author.text.clear()
        sp_students_name.setSelection(0)
    }

    private fun hideSofKeyboard() {
        val input = getSystemService(InputMethodManager::class.java) as InputMethodManager
        input.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.book_register_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_select_date -> displayDatePicker()
            R.id.menu_select_time -> displayTimePicker()
            R.id.menu_save -> saveBook()
            else -> onNavigateUp()
        }
        return true
    }

    private fun saveBook() {
        viewModel.saveBook(et_book_name.text.toString(), ed_book_author?.text.toString())
        viewModel.onSaveMenuClick()
    }

    private fun displayTimePicker() {

        val calendar = Calendar.getInstance()
        val timePickerListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            viewModel.setTime(hour, minute)
        }

        TimePickerDialog(
            this,
            timePickerListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun displayDatePicker() {
        val calendar = Calendar.getInstance()

        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.setDate(year, month, day)
        }

        DatePickerDialog(
            this,
            datePickerListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_YEAR)
        ).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.setStudentId(p2)
        tv_selected_borrower_name.text = sp_students_name.selectedItem.toString()
    }
}
