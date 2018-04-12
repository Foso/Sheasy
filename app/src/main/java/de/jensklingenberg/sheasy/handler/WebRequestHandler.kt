package de.jensklingenberg.sheasy.handler

import android.content.Context
import android.util.Log
import de.jensklingenberg.sheasy.utils.FUtils
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 14/2/18.
 */

class WebRequestHandler {


    companion object {

        val MEDIA = "/web/"

        fun handle(context: Context, requestV1: String): NanoHTTPD.Response? {
            return when {
                requestV1.isEmpty() -> {
                    FUtils.returnAssetFile(context, "web/index.html")
                }
                else -> {
                    FUtils.returnAssetFile(context, requestV1)
                }
            }


        }


    }


}

