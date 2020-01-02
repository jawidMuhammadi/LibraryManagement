package com.example.library.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import androidx.core.app.NotificationManagerCompat
import com.example.library.student_register.StudentRegisterActivity
import com.example.library.utils.REGISTER_ACTION
import com.example.library.utils.SNOOZE_ACTION
import com.example.library.utils.STUDENT_ID
import com.example.library.utils.scheduleNotification
import java.util.*

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val studentId = intent.getLongExtra(STUDENT_ID, 0L)
        when (intent.action) {
            SNOOZE_ACTION -> snoozeForOneMinute(context, studentId)
            REGISTER_ACTION -> displayRegisterActivity(context)
        }

        with(NotificationManagerCompat.from(context)) {
            cancelAll()
        }
    }

    private fun displayRegisterActivity(context: Context) {
        val intent = Intent(context, StudentRegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    private fun snoozeForOneMinute(context: Context, studentId: Long) {
        val time = Calendar.getInstance().timeInMillis.apply {
            plus(DateUtils.SECOND_IN_MILLIS * 10)
        }

        scheduleNotification(context, studentId, time)
    }
}