package com.foxmobile.foxnote

import android.app.Application
import com.foxmobile.foxnote.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FoxNote: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FoxNote)
            modules(appModule)
        }
    }

}