package com.bitquartet.tgscheduler.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bitquartet.tgscheduler.receiver.AlarmReceiver;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "AlarmReceiver";
    private static int PENDING_ID = 1034434;
    public static final String PREFS_NAME = "AppDataStorage";
    public static final String REPEAT = "REPEAT";
    public static final String MINUTES = "MINUTES";
    private SharedPreferences mSettings;
    private EditText mEditText;
    private EditText mEditText2;
    private AlarmManager alarmMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mEditText.setText(Integer.toString(mSettings.getInt(REPEAT, 4)), TextView.BufferType.EDITABLE);
        mEditText2.setText(Integer.toString(mSettings.getInt(MINUTES, 5)), TextView.BufferType.EDITABLE);
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
    }

    public void saveAndEnable(View view) {
        String message = "Information saved";

        String repeat = mEditText.getText().toString();
        String minutes = mEditText2.getText().toString();

        if (repeat.length() > 0 && minutes.length() > 0) {

            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt(REPEAT, Integer.valueOf(repeat));
            editor.putInt(MINUTES, Integer.valueOf(minutes));
            editor.commit();


            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            intent.putExtra("Test", repeat);
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis() + 60*1000L,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES, getAlarmReceiverIntent(PendingIntent.FLAG_UPDATE_CURRENT));
        } else
            message = "You cannot leave empty fields";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void verifyAlarm(View view) {
        boolean alarmUp = (getAlarmReceiverIntent(PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp) {
            Log.d(TAG, "Alarm is active...");
            PendingIntent pendingIntent = getAlarmReceiverIntent(PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.cancel();
            alarmMgr.cancel(pendingIntent);
        }
        else {
            Log.d(TAG, "Alarm is not active");
        }
    }

    private PendingIntent getAlarmReceiverIntent(int flag) {
        return PendingIntent.getBroadcast(getApplicationContext(), PENDING_ID, new Intent(getApplicationContext(), AlarmReceiver.class), flag);
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
