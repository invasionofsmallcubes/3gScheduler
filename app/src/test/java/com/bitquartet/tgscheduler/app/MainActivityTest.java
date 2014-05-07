package com.bitquartet.tgscheduler.app;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlarmManager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private Spinner timings;
    private Spinner duration;
    private MainActivity mainActivity;

    @Before
    public void setup(){
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

        SharedPreferences sharedPreferences = Robolectric.application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        assertThat(sharedPreferences.getInt(MainActivity.REPEAT,0),is(1));
        assertThat(sharedPreferences.getInt(MainActivity.MINUTES,0),is(1));
    }

}