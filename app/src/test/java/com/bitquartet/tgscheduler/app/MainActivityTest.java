package com.bitquartet.tgscheduler.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import com.robolectric.runner.RobolectricGradleTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@Config(emulateSdk = 18)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    private Spinner timings;
    private Spinner duration;
    private MainActivity mainActivity;
    private Context mockContext;
    private AlarmManager mockAlarmManager;

    @Before
    public void setup(){
        TestNSApplication testNSApplication = (TestNSApplication) Robolectric.application;
        mockContext = testNSApplication.getApplicationContext();
        mockAlarmManager = testNSApplication.getAlarmManager();
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        timings = (Spinner) mainActivity.findViewById(R.id.timingsSpinner);
        duration = (Spinner) mainActivity.findViewById(R.id.durationSpinner);
    }

    @Test
    public void iHaveDefaultData() throws Exception {
        assertThat(timings.getSelectedItem().toString(),is("15"));
        assertThat(duration.getSelectedItem().toString(),is("5"));
    }

    @Test
    public void iCanEditAndSave() throws Exception {
        Button button = (Button) mainActivity.findViewById(R.id.button);
        timings.setSelection(1);
        duration.setSelection(1);
        button.performClick();
        verify(mockAlarmManager).setRepeating(eq(AlarmManager.ELAPSED_REALTIME_WAKEUP), eq(SystemClock.elapsedRealtime() + MainActivity.TWO_MINUTES),
                eq((1 + 1) * AlarmManager.INTERVAL_FIFTEEN_MINUTES), any(PendingIntent.class));

        SharedPreferences sharedPreferences = Robolectric.application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        assertThat(sharedPreferences.getInt(MainActivity.REPEAT,0),is(1));
        assertThat(sharedPreferences.getInt(MainActivity.MINUTES,0),is(1));
    }

}