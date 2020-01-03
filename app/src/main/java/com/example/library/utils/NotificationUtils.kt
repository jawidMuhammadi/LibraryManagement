package com.example.library.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.PersistableBundle
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.library.R
import com.example.library.notification.NotificationReceiver
import com.example.library.notification.NotificationActionReceiver
import com.example.library.notification.NotificationService


fun sendNotification(
    context: Context,
    studentId: Long,
    pendingIntent: PendingIntent
) {
    val builder = NotificationCompat.Builder(
        context, CHANNEL_ID
    ).apply {
        setSmallIcon(R.drawable.ic_access_time_black_24dp)
        setContentText("it is time to return book")
        setContentTitle("Return book")
        setContentIntent(pendingIntent)
        setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            priority = NotificationManager.IMPORTANCE_DEFAULT
        }
        addAction(
            R.drawable.ic_add_black_24dp,
            context.getString(R.string.snooze),
            getSnoozeAction(context, studentId)
        )
        addAction(
            R.drawable.ic_add_black_24dp,
            context.getString(R.string.register_book),
            getRegisterAction(context)
        )
        setStyle(getBigPictureStyle(context))
    }

    with(NotificationManagerCompat.from(context)) {
        notify(NOTIFICATION_ID, builder.build())
    }
}

private fun getBigPictureStyle(context: Context): NotificationCompat.Style? {
    return NotificationCompat.BigPictureStyle().run {
        bigPicture(
            getBitmapFromDrawable(context, R.drawable.ic_book)
        )
        bigLargeIcon(null)
        setBigContentTitle(context.getString(R.string.big_picture_content_tile))
        setSummaryText(context.getString(R.string.big_pic_summery))
    }
}

fun getBitmapFromDrawable(context: Context, icBook: Int): Bitmap? {
    val drawable = ContextCompat.getDrawable(context, icBook) as Drawable

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

private fun getRegisterAction(context: Context): PendingIntent? {
    val intent = Intent(context, NotificationActionReceiver::class.java).apply {
        action = REGISTER_ACTION
    }
    return PendingIntent.getBroadcast(
        context, 1, intent, 0
    )
}

private fun getSnoozeAction(context: Context, studentId: Long): PendingIntent {
    val intent = Intent(context, NotificationActionReceiver::class.java).apply {
        putExtra(STUDENT_ID, studentId)
        action = SNOOZE_ACTION
    }
    return PendingIntent.getBroadcast(context, 0, intent, 0)
}

fun createChannel(
    context: Context,
    channelName: String,
    channelId: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setShowBadge(false)
            description = context.getString(R.string.channel_name)
            lightColor = Color.BLUE
        }

        with(NotificationManagerCompat.from(context)) {
            createNotificationChannel(channel)
        }
    }
}

fun scheduleNotification(context: Context, studentId: Long, time: Long) {
    val alarmManager = ContextCompat.getSystemService(
        context,
        AlarmManager::class.java
    ) as AlarmManager

    AlarmManagerCompat.setExactAndAllowWhileIdle(
        alarmManager, AlarmManager.RTC_WAKEUP, time, getPendingIntent(context, studentId)
    )

}

private fun getPendingIntent(context: Context, studentId: Long): PendingIntent {
    val intent = Intent(context, NotificationReceiver::class.java)
    intent.putExtra(STUDENT_ID, studentId)
    return PendingIntent.getBroadcast(
        context, 0, intent, 0
    )
}

fun scheduleNotificationService(context: Context, packageName: String) {
    val component = ComponentName(
        packageName, NotificationService::class.java.name
    )

    val extra = PersistableBundle().apply {
        putLong(STUDENT_ID, 10L)
    }

    val jobInfo = JobInfo.Builder(
        JOB_ID,
        component
    ).apply {
        setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        setRequiresCharging(false)
        setExtras(extra)
    }

    val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    jobScheduler.schedule(jobInfo.build())
}

