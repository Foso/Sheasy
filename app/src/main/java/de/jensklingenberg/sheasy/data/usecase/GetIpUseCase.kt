package de.jensklingenberg.sheasy.data.usecase

import android.net.wifi.WifiManager
import android.text.format.Formatter

class GetIpUseCase(val wifiManager: WifiManager) {

    fun getIP(): String {

        return Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
    }


}