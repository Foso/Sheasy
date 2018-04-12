package de.jensklingenberg.sheasy.handler

import android.content.Context
import android.content.Intent
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.network.MyHttpServer
import de.jensklingenberg.sheasy.utils.DeviceUtils
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 14/2/18.
 */

class DeviceRequestHandler {


    companion object {

        val RESOURCE = "/device/"

        fun handle(context: Context, requestV1: String): NanoHTTPD.Response? {
            var mediaRequest = requestV1.substringAfter(RESOURCE);
            when {
                mediaRequest.isEmpty() -> {
                    App.instance.sendBroadcast("Device Info REQUESTED",mediaRequest)
                    return DeviceUtils.getDeviceInfo()
                }
            }

            return NanoHTTPD.newFixedLengthResponse("DeviceCommand $mediaRequest not found")

        }


    }


}


