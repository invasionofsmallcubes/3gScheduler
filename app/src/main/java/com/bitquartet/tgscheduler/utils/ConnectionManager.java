package com.bitquartet.tgscheduler.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ConnectionManager {

  public static void setConnection(Context context, boolean state) {
    try {
      final ConnectivityManager
          conman =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      final Class conmanClass;
      conmanClass = Class.forName(conman.getClass().getName());
      final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
      iConnectivityManagerField.setAccessible(true);
      final Object iConnectivityManager = iConnectivityManagerField.get(conman);
      final Class
          iConnectivityManagerClass =
          Class.forName(iConnectivityManager.getClass().getName());
      final Method
          setMobileDataEnabledMethod =
          iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
      setMobileDataEnabledMethod.setAccessible(true);
      setMobileDataEnabledMethod.invoke(iConnectivityManager, state);
    } catch (Exception e) {
      Log.e("ConnectionManager", "Error while trying to enable/disable the connection", e);
    }
  }
}
