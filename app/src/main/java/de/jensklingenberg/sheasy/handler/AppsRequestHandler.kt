package de.jensklingenberg.sheasy.handler

import android.content.Context
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.utils.AppUtils
import fi.iki.elonen.NanoHTTPD


/**
 * Created by jens on 14/2/18.
 */

class AppsRequestHandler {


    companion object {

        val RESOURCE = "/apps/"


        fun handle(context: Context,requestV1: String): NanoHTTPD.Response? {
            val listMyData = Types.newParameterizedType(kotlin.collections.List::class.java, AppsResponse::class.java)
            val adapter = App.instance.moshi?.adapter<kotlin.collections.List<AppsResponse>>(listMyData)
            App.instance.sendBroadcast("APPS REQUESTED", requestV1.substringAfter(RESOURCE))

            val handleApps = AppUtils.handleApps(context)
            return NanoHTTPDExt.debugResponse(adapter?.toJson(handleApps)?:"")

        }


    }


}


