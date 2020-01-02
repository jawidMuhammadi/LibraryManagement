package com.example.library.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.library.R
import com.example.library.student_details.StudentDetailsActivity
import com.example.library.utils.CHANNEL_ID
import com.example.library.utils.STUDENT_ID
import com.example.library.utils.createChannel
import com.example.library.utils.sendNotification

class NotificationWorker(
    val context: Context,
    workParams: WorkerParameters
) : Worker(context, workParams) {

    private fun getPendingIntent(context: Context, studentId: Long): PendingIntent {
        val intent = Intent(context, StudentDetailsActivity::class.java)
        intent.putExtra(STUDENT_ID, studentId)
        return PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun doWork(): Result {

        val studentId = inputData.getLong(STUDENT_ID, 0L)
        val pendingIntent = getPendingIntent(context, studentId)
        /**
         * IT is a good practice to call createChannel Function frequently.
         * It doesn't need any operation if we call after first creation
         * */
        createChannel(context, context.getString(R.string.channel_name), CHANNEL_ID)
        sendNotification(context, studentId, pendingIntent)

        return Result.success()
    }
}