package com.example.library.notification

import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import com.example.library.book_details.BookDetailsActivity
import com.example.library.utils.STUDENT_ID
import com.example.library.utils.sendNotification

class NotificationService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        val studentId = jobParameters?.extras?.getLong(STUDENT_ID, 0L)
        studentId?.let { sendNotification(this, it, getPendingIntent(this, studentId)) }
        return false
    }

    private fun getPendingIntent(
        context: Context,
        studentId: Long
    ): PendingIntent {
        val intent = Intent(context, BookDetailsActivity::class.java).apply {
            putExtra(STUDENT_ID, studentId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        return PendingIntent.getActivity(
            context, 0, intent, 0
        )
    }
}