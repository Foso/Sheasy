package de.jensklingenberg.sheasy.legacy.utils

import android.content.Context
import android.text.format.Formatter
import de.jensklingenberg.sheasy.legacy.utils.extension.wifiManager

/**
 * Created by jens on 24/2/18.
 */

class NetworkUtils {
    companion object {
        fun getIP(context: Context): String? {
            val wm = context.wifiManager()
            val ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
            return ip
        }


    }
}