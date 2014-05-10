package com.bitquartet.tgscheduler.app;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import com.robolectric.runner.RobolectricGradleTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityPreStoredDataTest {

    @Test
    public void iCanPopulateData() throws Exception {
        SharedPreferences sharedPreferences = Robolectric.application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(MainActivity.REPEAT, 0).commit();
        sharedPreferences.edit().putInt(MainActivity.MINUTES, 0).commit();
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        Spinner editText = (Spinner)mainActivity.findViewById(R.id.timingsSpinner);
        assertThat(editText.getSelectedItem().toString(),is("15"));

        Spinner editText2 = (Spinner)mainActivity.findViewById(R.id.durationSpinner);
        assertThat(editText2.getSelectedItem().toString(),is("5"));
    }
}
