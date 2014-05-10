package com.bitquartet.tgscheduler.app;

import android.app.AlarmManager;
import android.content.Context;

import static org.mockito.Mockito.mock;

public class TestNSApplication extends NSApplication {

    private static Context context = mock(Context.class);
    private static AlarmManager alarmManager = mock(AlarmManager.class);

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public AlarmManager getAlarmManager() {
        return alarmManager;
    }
}
