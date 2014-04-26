package com.bitquartet.tgscheduler.app;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MainActivityPreStoredDataTest {

    @Test
    public void iCanPopulateData() throws Exception {
        SharedPreferences sharedPreferences = Robolectric.application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(MainActivity.REPEAT, 10).commit();
        sharedPreferences.edit().putInt(MainActivity.MINUTES, 13).commit();
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        EditText editText = (EditText)mainActivity.findViewById(R.id.editText);
        assertThat(editText.getText().toString(),is("10"));

        EditText editText2 = (EditText)mainActivity.findViewById(R.id.editText2);
        assertThat(editText2.getText().toString(),is("13"));
    }
}
