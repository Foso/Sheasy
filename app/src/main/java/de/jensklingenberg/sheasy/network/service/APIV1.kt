package de.jensklingenberg.sheasy.network.service

import android.view.KeyEvent
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.network.service.apiv1.file
import de.jensklingenberg.sheasy.network.service.apiv1.media
import de.jensklingenberg.sheasy.utils.*
import de.jensklingenberg.sheasy.utils.extension.getAudioManager
import de.jensklingenberg.sheasy.utils.extension.toJson
import io.ktor.application.call
import io.ktor.content.PartData
import io.ktor.content.forEachPart
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.request.receiveMultipart
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import java.io.File
import java.io.FileInputStream


fun Route.contacts(app: App, moshi: Moshi) {
    val CONTACTS = "contacts"

    get(CONTACTS) {
        app.sendBroadcast(EventCategory.REQUEST, "/contacts")

        val contacts = ContactUtils.readContacts(app.contentResolver)
        val response = moshi.toJson(contacts)
        call.respondText(response, ContentType.Text.JavaScript)
    }
}


fun Route.device(moshi: Moshi) {
    get("device") {
        App.instance.sendBroadcast(
            EventCategory.REQUEST,
            "Device Info REQUESTED"
        )
        val deviceInfo = DeviceUtils.getDeviceInfo()


        call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
        call.respondText(
            moshi?.toJson(deviceInfo) ?: "",
            ContentType.Text.JavaScript
        )
    }
}


fun Route.apps(app: App, moshi: Moshi) {
    val APPS = "apps"
    get(APPS) {
        app.sendBroadcast(EventCategory.REQUEST, "/apps")

        val appsResponse =
            moshi.toJson(AppUtils.getAppsResponseList(app))


        call.apply {
            response.header(HttpHeaders.AccessControlAllowOrigin, "*")
            respondText(appsResponse, ContentType.Text.JavaScript)
        }


    }
}