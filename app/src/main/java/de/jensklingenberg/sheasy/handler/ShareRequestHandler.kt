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

class ShareRequestHandler {


    companion object {

        val RESOURCE = "/share/"

        fun handle(requestV1: String): NanoHTTPD.Response? {
            App.instance.sendBroadcast("SHARE", requestV1.substringAfter(RESOURCE))
            return NanoHTTPD.newFixedLengthResponse("ShareCommand$RESOURCE not found")

        }


    }


}


