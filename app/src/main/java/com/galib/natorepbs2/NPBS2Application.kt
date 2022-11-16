package com.galib.natorepbs2

import android.app.Application
import timber.log.Timber

class NPBS2Application : Application() {
   override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}