package com.example.library.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.library.R
import com.example.library.book_register.BookRegisterActivity
import com.example.library.student_register.StudentRegisterActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProviders.of(this).get(RegisterActivityViewModel::class.java)

        subscribeUI()

        btn_register_book.setOnClickListener {
            viewModel.onBookRegisterClick()
        }

        btn_register_student.setOnClickListener {
            viewModel.onOnstudentRegisterClick()
        }

    }

    private fun subscribeUI() {
        viewModel.onBookRegisterClicked.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, BookRegisterActivity::class.java)
                startActivity(intent)
            }
        })

        viewModel.onStudentRegisterClicked.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val intent = Intent(this, StudentRegisterActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
