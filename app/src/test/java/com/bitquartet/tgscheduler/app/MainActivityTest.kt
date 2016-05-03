package com.bitquartet.tgscheduler.app

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import android.widget.Button
import android.widget.Spinner

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.mockito.Matchers.any
import org.mockito.Matchers.eq
import org.mockito.Mockito.verify
import org.robolectric.RuntimeEnvironment.application

@Config(sdk = intArrayOf(21), constants = BuildConfig::class)
@RunWith(RobolectricGradleTestRunner::class)
class MainActivityTest {

    private var timings: Spinner? = null
    private var duration: Spinner? = null
    private var mockAlarmManager: AlarmManager? = null
    private var saveAndEnable: Button? = null
    private var verifyAlarm: Button? = null

    @Before
    fun setUp() {
        val testNSApplication = application as TestNSApplication
        mockAlarmManager = testNSApplication.alarmManager
        val mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        timings = mainActivity.findViewById(R.id.timingsSpinner) as Spinner
        duration = mainActivity.findViewById(R.id.durationSpinner) as Spinner
        saveAndEnable = mainActivity.findViewById(R.id.saveAndEnable) as Button
        verifyAlarm = mainActivity.findViewById(R.id.verifyAlarm) as Button
    }

    @Test
    @Throws(Exception::class)
    fun iHaveDefaultData() {
        assertThat(timings!!.selectedItem.toString(), `is`("15"))
        assertThat(duration!!.selectedItem.toString(), `is`("5"))
    }

    @Test
    @Throws(Exception::class)
    fun iCanEditAndSave() {

        timings!!.setSelection(1)
        duration!!.setSelection(1)


        saveAndEnable!!.performClick()

        verify<AlarmManager>(mockAlarmManager).setRepeating(eq(AlarmManager.ELAPSED_REALTIME_WAKEUP),
                eq(SystemClock.elapsedRealtime() + MainActivity.TWO_MINUTES),
                eq((1 + 1) * AlarmManager.INTERVAL_FIFTEEN_MINUTES),
                any(PendingIntent::class.java))

        val sharedPreferences = application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)
        assertThat(sharedPreferences.getInt(MainActivity.REPEAT, 0), `is`(1))
        assertThat(sharedPreferences.getInt(MainActivity.MINUTES, 0), `is`(1))
    }

    @Test
    @Ignore(value = "Need to refactor PendingIntent in a collaborator")
    fun iCanDisableASchedule() {

        timings!!.setSelection(1)
        duration!!.setSelection(1)

        verifyAlarm!!.performClick()

        verify<AlarmManager>(mockAlarmManager).cancel(any(PendingIntent::class.java))

    }

}