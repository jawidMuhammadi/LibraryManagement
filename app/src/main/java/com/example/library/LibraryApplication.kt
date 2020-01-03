package com.example.library

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.library.notification.NotificationService
import com.example.library.settings.ThemType
import com.example.library.utils.JOB_ID
import com.example.library.utils.STUDENT_ID

class LibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val appTheme = PreferenceManager.getDefaultSharedPreferences(this).getString(
            getString(R.string.key_pref_app_theme),
            getString(R.string.pref_default_value)
        )
        when (appTheme) {
            ThemType.DEFAULT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemType.LIGHT.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemType.DARK.value -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

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

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(jobInfo.build())
    }
}