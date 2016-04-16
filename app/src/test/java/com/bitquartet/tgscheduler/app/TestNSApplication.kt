package com.bitquartet.tgscheduler.app

import android.app.AlarmManager
import android.content.Context

import org.mockito.Mockito.mock

class TestNSApplication : NSApplication() {

    override val context: Context
        get() = mockedContext

    override val alarmManager: AlarmManager
        get()  = mockedAlarmManager

    companion object {
        private val mockedContext = mock(Context::class.java)
        private val mockedAlarmManager = mock(AlarmManager::class.java)
    }
}
