package com.daveanthonythomas.technicalassessment.db

import androidx.room.TypeConverter
import java.util.*


class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}