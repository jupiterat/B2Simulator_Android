package com.tech.b2simulator.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class BootReceiver : BroadcastReceiver() {
    /*
    * restart reminders alarms when user's device reboots
    * */
    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive")
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            RemindersManager.startReminder(context)
        }
    }
}