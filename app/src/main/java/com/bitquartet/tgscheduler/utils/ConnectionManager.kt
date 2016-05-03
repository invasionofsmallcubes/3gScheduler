package com.bitquartet.tgscheduler.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

object ConnectionManager {

    fun setConnection(context: Context, state: Boolean) {
        try {
            val conman = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val conmanClass: Class<Any>
            conmanClass = Class.forName(conman.javaClass.name) as Class<Any>
            val iConnectivityManagerField = conmanClass.getDeclaredField("mService")
            iConnectivityManagerField.isAccessible = true
            val iConnectivityManager = iConnectivityManagerField.get(conman)
            val iConnectivityManagerClass = Class.forName(iConnectivityManager.javaClass.name)
            val setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", java.lang.Boolean.TYPE)
            setMobileDataEnabledMethod.isAccessible = true
            setMobileDataEnabledMethod.invoke(iConnectivityManager, state)
        } catch (e: Exception) {
            Log.e("ConnectionManager", "Error while trying to enable/disable the connection", e)
        }

    }
}
