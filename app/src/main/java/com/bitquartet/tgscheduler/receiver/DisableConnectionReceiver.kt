package com.bitquartet.tgscheduler.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

import com.bitquartet.tgscheduler.utils.ConnectionManager

class DisableConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("I'm called", TAG)
        ConnectionManager.setConnection(context, false)
    }

    companion object {

        private val TAG = "DisableConnectionReceiver"
    }
}
