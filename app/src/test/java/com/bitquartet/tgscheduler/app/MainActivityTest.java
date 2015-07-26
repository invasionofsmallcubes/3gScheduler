package com.bitquartet.tgscheduler.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Spinner;

import com.robolectric.runner.RobolectricGradleTestRunner;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
  private AlarmManager mockAlarmManager;
  private Button saveAndEnable;
  private Button verifyAlarm;

  @Before
  public void setup() {
    TestNSApplication testNSApplication = (TestNSApplication) Robolectric.application;
    mockAlarmManager = testNSApplication.getAlarmManager();
    mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    timings = (Spinner) mainActivity.findViewById(R.id.timingsSpinner);
    duration = (Spinner) mainActivity.findViewById(R.id.durationSpinner);
    saveAndEnable = (Button) mainActivity.findViewById(R.id.saveAndEnable);
    verifyAlarm = (Button) mainActivity.findViewById(R.id.verifyAlarm);
  }

  @Test
  public void iHaveDefaultData() throws Exception {
    assertThat(timings.getSelectedItem().toString(), is("15"));
    assertThat(duration.getSelectedItem().toString(), is("5"));
  }

  @Test
  public void iCanEditAndSave() throws Exception {

    timings.setSelection(1);
    duration.setSelection(1);


    saveAndEnable.performClick();

    verify(mockAlarmManager).setRepeating(eq(AlarmManager.ELAPSED_REALTIME_WAKEUP),
                                          eq(SystemClock.elapsedRealtime()
                                             + MainActivity.TWO_MINUTES),
                                          eq((1 + 1) * AlarmManager.INTERVAL_FIFTEEN_MINUTES),
                                          any(PendingIntent.class)
    );

    SharedPreferences
        sharedPreferences =
        Robolectric.application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
    assertThat(sharedPreferences.getInt(MainActivity.REPEAT, 0), is(1));
    assertThat(sharedPreferences.getInt(MainActivity.MINUTES, 0), is(1));
  }

  @Test
  public void iCanDisableASchedule() {

    timings.setSelection(1);
    duration.setSelection(1);

    verifyAlarm.performClick();

    verify(mockAlarmManager).cancel(any(PendingIntent.class));

  }

}