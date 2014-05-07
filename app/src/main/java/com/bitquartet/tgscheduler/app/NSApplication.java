package com.bitquartet.tgscheduler.app;

import android.app.Application;
import android.content.Context;

public class NSApplication extends Application {

    public Context getContext() {
        return getApplicationContext();
    }

}
