package com.bitquartet.tgscheduler.app


import android.content.Context
import android.content.SharedPreferences
import android.widget.Spinner

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.robolectric.RuntimeEnvironment.application

@Config(sdk = intArrayOf(21), constants = BuildConfig::class)
@RunWith(RobolectricGradleTestRunner::class)
class MainActivityPreStoredDataTest {

    @Test
    @Throws(Exception::class)
    fun iCanPopulateData() {
        val sharedPreferences = application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(MainActivity.REPEAT, 0).apply()
        sharedPreferences.edit().putInt(MainActivity.MINUTES, 0).apply()
        val mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()

        val editText = mainActivity.findViewById(R.id.timingsSpinner) as Spinner
        assertThat(editText.selectedItem.toString(), `is`("15"))

        val editText2 = mainActivity.findViewById(R.id.durationSpinner) as Spinner
        assertThat(editText2.selectedItem.toString(), `is`("5"))
    }
}
