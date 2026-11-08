package com.stfashion.creativehouse.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.stfashion.creativehouse.workers.FileExpirationWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON"
        ) {
            // Re-schedule the 30-day file expiration periodic task after device restart
            FileExpirationWorker.schedulePeriodicCleanup(context)
        }
    }
}
