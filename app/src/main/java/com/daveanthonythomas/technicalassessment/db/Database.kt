package com.daveanthonythomas.technicalassessment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daveanthonythomas.technicalassessment.item.model.Item
import com.daveanthonythomas.technicalassessment.item.model.ItemDAO

@Database(entities = [(Item::class)], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun getItemDAO(): ItemDAO
}