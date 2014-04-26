package com.bitquartet.tgscheduler.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private EditText editText;
    private EditText editText2;
    private MainActivity mainActivity;

    @Before
    public void setup(){
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        editText = (EditText) mainActivity.findViewById(R.id.editText);
        editText2 = (EditText) mainActivity.findViewById(R.id.editText2);
    }

    @Test
    public void iHaveDefaultData() throws Exception {
        assertThat(editText.getText().toString(),is("4"));
        assertThat(editText2.getText().toString(),is("5"));
    }

    @Test
    public void iCanEditAndSave() throws Exception {
        Button button = (Button) mainActivity.findViewById(R.id.button);

        editText.setText("20");
        editText2.setText("10");
        button.performClick();

        SharedPreferences sharedPreferences = Robolectric.application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        assertThat(sharedPreferences.getInt(MainActivity.REPEAT,4),is(20));
        assertThat(sharedPreferences.getInt(MainActivity.MINUTES,4),is(10));
    }

    @Test
    public void iCannotSetEmptyData() throws Exception {
        Button button = (Button) mainActivity.findViewById(R.id.button);

        editText.setText("");
        editText2.setText("10");
        button.performClick();

        SharedPreferences sharedPreferences = Robolectric.application.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        assertThat(sharedPreferences.getInt(MainActivity.REPEAT,4),is(4));
        assertThat(sharedPreferences.getInt(MainActivity.MINUTES,4),is(4));
    }

}