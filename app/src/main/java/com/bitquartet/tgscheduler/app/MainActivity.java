package com.bitquartet.tgscheduler.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitquartet.tgscheduler.receiver.EnableConnectionReceiver;


public class MainActivity extends ActionBarActivity {

  private static final String TAG = "AlarmReceiver";
  public static final String
      COM_BITQUARTET_TGSCHEDULER_ENABLE =
      "com.bitquartet.tgscheduler.ENABLE";
  public static int PENDING_ID = 1034434;
  public static long TWO_MINUTES = 120000L;
  public static final String PREFS_NAME = "AppDataStorage";
  public static final String REPEAT = "REPEAT";
  public static final String MINUTES = "MINUTES";
  private SharedPreferences mSettings;
  private Spinner timings;
  private Spinner duration;
  private AlarmManager alarmMgr;
  private Context mContext;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mSettings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    timings = (Spinner) findViewById(R.id.timingsSpinner);
    duration = (Spinner) findViewById(R.id.durationSpinner);
    timings.setSelection(mSettings.getInt(REPEAT, 0));
    duration.setSelection(mSettings.getInt(MINUTES, 0));
    NSApplication mApplication = (NSApplication) getApplication();
    mContext = mApplication.getContext();
    alarmMgr = mApplication.getAlarmManager();
  }

  public void saveAndEnable(View view) {

    int repeat = timings.getSelectedItemPosition();
    int minutes = duration.getSelectedItemPosition();

    SharedPreferences.Editor editor = mSettings.edit();
    editor.putInt(REPEAT, repeat);
    editor.putInt(MINUTES, minutes);
    editor.commit();

    Intent intent = new Intent(getApplicationContext(), EnableConnectionReceiver.class);
    intent.setAction(COM_BITQUARTET_TGSCHEDULER_ENABLE);
    intent.putExtra("duration", minutes);

    Log.d("Interval is: " + (repeat + 1) * AlarmManager.INTERVAL_FIFTEEN_MINUTES, TAG);

    alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                          SystemClock.elapsedRealtime() + TWO_MINUTES,
                          (repeat + 1) * AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                          getAlarmReceiverIntent(intent, PendingIntent.FLAG_UPDATE_CURRENT));

    Toast
        .makeText(mContext, getResources().getString(R.string.informationSaved), Toast.LENGTH_SHORT)
        .show();
  }

  public void verifyAlarm(View view) {

    Intent intent = new Intent(mContext, EnableConnectionReceiver.class);
    intent.setAction(COM_BITQUARTET_TGSCHEDULER_ENABLE);

    if (getAlarmReceiverIntent(intent, PendingIntent.FLAG_NO_CREATE) != null) {
      PendingIntent
          pendingIntent =
          getAlarmReceiverIntent(intent, PendingIntent.FLAG_UPDATE_CURRENT);
      pendingIntent.cancel();
      alarmMgr.cancel(pendingIntent);
    }

  }

  private PendingIntent getAlarmReceiverIntent(Intent intent, int flag) {
    return PendingIntent.getBroadcast(mContext, PENDING_ID, intent, flag);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
