package com.bitquartet.tgscheduler.app;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import com.bitquartet.tgscheduler.utils.ConnectionManager;

public class NSApplication extends Application {

  public Context getContext() {
    return getApplicationContext();
  }

  public AlarmManager getAlarmManager() {
    return (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
  }
}
