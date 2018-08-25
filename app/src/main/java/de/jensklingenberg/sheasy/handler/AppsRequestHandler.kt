package de.jensklingenberg.sheasy.handler

import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.utils.AppUtils
import de.jensklingenberg.sheasy.utils.extension.toJson


/**
 * Created by jens on 14/2/18.
 */

class AppsRequestHandler(val app: App, val moshi: Moshi) {
    companion object {
        val RESOURCE = "/apps/"
    }


    fun handle(requestV1: String): String {
        app.sendBroadcast(EventCategory.REQUEST, "/apps")
        return moshi.toJson(AppUtils.getAppsResponseList(app))
    }


}


