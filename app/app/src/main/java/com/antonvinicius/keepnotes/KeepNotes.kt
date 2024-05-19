package com.antonvinicius.keepnotes

import android.app.Application
import com.antonvinicius.keepnotes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KeepNotes : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KeepNotes)
            modules(appModule)
        }
    }
}