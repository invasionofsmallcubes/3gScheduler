package com.bitquartet.tgscheduler.app;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.RuntimeEnvironment.application;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityPreStoredDataTest {

  @Test
  public void iCanPopulateData() throws Exception {
    SharedPreferences
        sharedPreferences =
            application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
    sharedPreferences.edit().putInt(MainActivity.REPEAT, 0).apply();
    sharedPreferences.edit().putInt(MainActivity.MINUTES, 0).apply();
    MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

    Spinner editText = (Spinner) mainActivity.findViewById(R.id.timingsSpinner);
    assertThat(editText.getSelectedItem().toString(), is("15"));

    Spinner editText2 = (Spinner) mainActivity.findViewById(R.id.durationSpinner);
    assertThat(editText2.getSelectedItem().toString(), is("5"));
  }
}
