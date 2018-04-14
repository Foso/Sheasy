package de.jensklingenberg.sheasy.handler

import de.jensklingenberg.sheasy.App
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


