package com.bitquartet.tgscheduler.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bitquartet.tgscheduler.utils.ConnectionManager;

public class DisableConnectionReceiver extends BroadcastReceiver {

  private static final String TAG = "DisableConnectionReceiver";

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d("I'm called", TAG);
    ConnectionManager.setConnection(context, false);
  }
}
