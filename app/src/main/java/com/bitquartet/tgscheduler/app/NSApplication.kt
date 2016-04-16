package com.bitquartet.tgscheduler.app

import android.app.AlarmManager
import android.app.Application
import android.content.Context

open class NSApplication : Application() {

    open val context: Context
        get() = applicationContext

    open val alarmManager: AlarmManager
        get() = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
}
