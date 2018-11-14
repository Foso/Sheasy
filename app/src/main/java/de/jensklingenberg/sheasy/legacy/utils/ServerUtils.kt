package de.jensklingenberg.sheasy.legacy.utils

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import de.jensklingenberg.sheasy.App

/**
 * Created by jens on 24/2/18.
 */

class ServerUtils {
    private fun isServiceRunning(context: App): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.d("THIS", service.service.className)


        }
        return false
    }
}

