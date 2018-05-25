package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import de.jensklingenberg.sheasy.App

/**
 * Created by jens on 24/2/18.
 */

class NetworkUtils {
    companion object {
        fun getIP(context: App): String? {
            val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
            return ip
        }


    }
}