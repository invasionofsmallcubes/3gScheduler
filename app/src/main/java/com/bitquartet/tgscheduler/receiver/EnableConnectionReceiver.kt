package com.bitquartet.tgscheduler.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log

import com.bitquartet.tgscheduler.utils.ConnectionManager

class EnableConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("I'm called", TAG)
        ConnectionManager.setConnection(context, intent.getBooleanExtra("state", true))

        val disableIntent = Intent(context, DisableConnectionReceiver::class.java)
        disableIntent.action = "com.bitquartet.tgscheduler.DISABLE"

        val broadcast = PendingIntent.getBroadcast(context, ONE_SHOT_ID, disableIntent, PendingIntent.FLAG_ONE_SHOT)
        Log.d("Duration is: " + intent.getIntExtra("duration", 0), TAG)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + FIVE_MINUTES * (intent.getIntExtra("duration", 0) + 1), broadcast)
    }

    companion object {

        private val TAG = "EnableConnectionReceiver"
        private val ONE_SHOT_ID = 111111
        private val FIVE_MINUTES = 5 * 60 * 1000
    }
}
