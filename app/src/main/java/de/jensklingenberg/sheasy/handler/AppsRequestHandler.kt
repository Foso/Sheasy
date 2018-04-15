package de.jensklingenberg.sheasy.handler

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.helpers.MoshiHelper
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.utils.AppUtils
import fi.iki.elonen.NanoHTTPD


/**
 * Created by jens on 14/2/18.
 */

class AppsRequestHandler(val app: App,val moshi: Moshi) {
    companion object {
        val RESOURCE = "/apps/"
    }



    fun handle(requestV1: String): NanoHTTPD.Response? {
        app.sendBroadcast("APPS REQUESTED", requestV1.substringAfter(RESOURCE))

        val appsResponse = MoshiHelper.appsToJson(moshi,AppUtils.handleApps(app))
        return NanoHTTPDExt.debugResponse(appsResponse)

    }


}


