package com.daveanthonythomas.technicalassessment

import android.app.Application
import com.daveanthonythomas.technicalassessment.di.AppDependencies
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TechnicalAssignmentApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TechnicalAssignmentApp)
            modules(AppDependencies.app)
        }
    }
}