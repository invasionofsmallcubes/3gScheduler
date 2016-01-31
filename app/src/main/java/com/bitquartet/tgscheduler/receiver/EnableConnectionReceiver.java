package com.bitquartet.tgscheduler.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.bitquartet.tgscheduler.utils.ConnectionManager;

public class EnableConnectionReceiver extends BroadcastReceiver {

  private static final String TAG = "EnableConnectionReceiver";
  private static int ONE_SHOT_ID = 111111;
  private static int FIVE_MINUTES = 5 * 60 * 1000;

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d("I'm called", TAG);
    ConnectionManager.setConnection(context, intent.getBooleanExtra("state", true));

    Intent disableIntent = new Intent(context, DisableConnectionReceiver.class);
    disableIntent.setAction("com.bitquartet.tgscheduler.DISABLE");

    PendingIntent
        broadcast =
        PendingIntent
            .getBroadcast(context, ONE_SHOT_ID, disableIntent, PendingIntent.FLAG_ONE_SHOT);
    Log.d("Duration is: " + intent.getIntExtra("duration", 0), TAG);
    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                 SystemClock.elapsedRealtime() + FIVE_MINUTES * (intent.getIntExtra("duration", 0)
                                                                 + 1), broadcast);
  }
}
