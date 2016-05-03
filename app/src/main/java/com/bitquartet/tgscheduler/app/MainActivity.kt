package com.bitquartet.tgscheduler.app

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.Toast

import com.bitquartet.tgscheduler.receiver.EnableConnectionReceiver


class MainActivity : Activity() {
    private var mSettings: SharedPreferences? = null
    private var timings: Spinner? = null
    private var duration: Spinner? = null
    private var alarmMgr: AlarmManager? = null
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSettings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        timings = findViewById(R.id.timingsSpinner) as Spinner
        duration = findViewById(R.id.durationSpinner) as Spinner
        timings!!.setSelection(mSettings!!.getInt(REPEAT, 0))
        duration!!.setSelection(mSettings!!.getInt(MINUTES, 0))
        val mApplication = application as NSApplication
        mContext = mApplication.context
        alarmMgr = mApplication.alarmManager
    }

    fun saveAndEnable(view: View) {

        val repeat = timings!!.selectedItemPosition
        val minutes = duration!!.selectedItemPosition

        val editor = mSettings!!.edit()
        editor.putInt(REPEAT, repeat)
        editor.putInt(MINUTES, minutes)
        editor.apply()

        val intent = Intent(applicationContext, EnableConnectionReceiver::class.java)
        intent.action = COM_BITQUARTET_TGSCHEDULER_ENABLE
        intent.putExtra("duration", minutes)

        Log.d("Interval is: " + (repeat + 1) * AlarmManager.INTERVAL_FIFTEEN_MINUTES, TAG)

        alarmMgr!!.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + TWO_MINUTES,
                (repeat + 1) * AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                getAlarmReceiverIntent(intent, PendingIntent.FLAG_UPDATE_CURRENT))

        Toast.makeText(mContext, resources.getString(R.string.informationSaved), Toast.LENGTH_SHORT).show()
    }

    fun verifyAlarm(view: View) {

        val intent = Intent(mContext, EnableConnectionReceiver::class.java)
        intent.action = COM_BITQUARTET_TGSCHEDULER_ENABLE

        if (getAlarmReceiverIntent(intent, PendingIntent.FLAG_NO_CREATE) != null) {
            val pendingIntent = getAlarmReceiverIntent(intent, PendingIntent.FLAG_UPDATE_CURRENT)
            pendingIntent?.cancel()
            alarmMgr!!.cancel(pendingIntent)
        }

    }

    private fun getAlarmReceiverIntent(intent: Intent, flag: Int): PendingIntent? {
        return PendingIntent.getBroadcast(mContext, PENDING_ID, intent, flag)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.itemId == R.id.action_settings || super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = "AlarmReceiver"
        @JvmField val COM_BITQUARTET_TGSCHEDULER_ENABLE = "com.bitquartet.tgscheduler.ENABLE"
        @JvmField val PENDING_ID = 1034434
        @JvmField val TWO_MINUTES = 120000L
        @JvmField val PREFS_NAME = "AppDataStorage"
        @JvmField val REPEAT = "REPEAT"
        @JvmField val MINUTES = "MINUTES"
    }
}
