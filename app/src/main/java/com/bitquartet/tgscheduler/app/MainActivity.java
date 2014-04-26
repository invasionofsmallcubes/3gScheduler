package com.bitquartet.tgscheduler.app;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "AppDataStorage";
    public static final String REPEAT = "REPEAT";
    public static final String MINUTES = "MINUTES";
    private SharedPreferences mSettings;
    private EditText mEditText;
    private EditText mEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mEditText.setText(Integer.toString(mSettings.getInt(REPEAT, 4)), TextView.BufferType.EDITABLE);
        mEditText2.setText(Integer.toString(mSettings.getInt(MINUTES, 5)), TextView.BufferType.EDITABLE);
    }

    public void saveAndEnable(View view) {
        String message = "Information saved";
        if (mEditText.getText().toString().length() > 0 && mEditText2.getText().toString().length() > 0) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt(REPEAT, Integer.valueOf(mEditText.getText().toString()));
            editor.putInt(MINUTES, Integer.valueOf(mEditText2.getText().toString()));
            editor.commit();
        } else
            message = "You cannot leave empty fields";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
