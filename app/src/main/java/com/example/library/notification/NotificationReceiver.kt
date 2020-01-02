package com.example.library.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.library.utils.STUDENT_ID

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val studentId = intent.getLongExtra(STUDENT_ID, 0L)

        val inputData = Data.Builder().apply {
            putLong(STUDENT_ID, studentId)
        }.build()

        val worRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java).apply {
            setInputData(inputData)
        }

        WorkManager.getInstance(context).enqueue(worRequest.build())
    }
}