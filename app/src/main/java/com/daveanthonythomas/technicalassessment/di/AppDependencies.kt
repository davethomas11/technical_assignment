package com.daveanthonythomas.technicalassessment.di

import androidx.room.Room
import com.daveanthonythomas.technicalassessment.db.Database
import org.koin.dsl.module

object AppDependencies {

    var app = module {
        single {
            Room.databaseBuilder(get(), Database::class.java, "technical-assignment-db").build()
        }

        single {
            val db: Database = get()
            db.getItemDAO()
        }
    }
}