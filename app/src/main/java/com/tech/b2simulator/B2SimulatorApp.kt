package com.tech.b2simulator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class B2SimulatorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}