package com.example.library.student_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media.app.NotificationCompat
import com.example.library.R
import kotlinx.android.synthetic.main.activity_student_details.*

class StudentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        setSupportActionBar(toolbar)

    }
}
