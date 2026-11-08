package com.stfashion.creativehouse

import android.app.Application
import com.stfashion.creativehouse.workers.FileExpirationWorker

class STFashionApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize background strict 30-day file expiration periodic scheduler via Android WorkManager
        FileExpirationWorker.schedulePeriodicCleanup(this)
    }
}
