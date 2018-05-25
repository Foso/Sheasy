package de.jensklingenberg.sheasy.handler

import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.helpers.MoshiHelper
import de.jensklingenberg.sheasy.utils.AppUtils


/**
 * Created by jens on 14/2/18.
 */

class AppsRequestHandler(val app: App, val moshi: Moshi) {
    companion object {
        val RESOURCE = "/apps/"
    }


    fun handle(requestV1: String): String {
        app.sendBroadcast(EventCategory.REQUEST, "/apps")

        val appsResponse = MoshiHelper.appsToJson(moshi, AppUtils.handleApps(app))
        // return NanoHTTPDExt.debugResponse(appsResponse)
        return appsResponse
    }


}


