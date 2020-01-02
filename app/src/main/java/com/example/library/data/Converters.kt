package com.example.library.data

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun calendarToDateStamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun dateStampToCalendar(time: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = time }
}