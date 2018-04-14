package de.jensklingenberg.sheasy.handler

import android.content.Context
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.model.DeviceResponse
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
                    val deviceInfo = DeviceUtils.getDeviceInfo()
                    val jsonAdapter =  App.instance.moshi?.adapter(DeviceResponse::class.java)
                    return NanoHTTPDExt.debugResponse(jsonAdapter?.toJson(deviceInfo)?:"")
                }
            }

            return NanoHTTPD.newFixedLengthResponse("DeviceCommand $mediaRequest not found")

        }


    }


}


