package de.jensklingenberg.sheasy.network.service.apiv1

import android.view.KeyEvent
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.utils.KeyUtils
import de.jensklingenberg.sheasy.utils.MediaUtils
import de.jensklingenberg.sheasy.utils.extension.getAudioManager
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.media(app: App, moshi: Moshi) {
    route("media") {
        val audioManager = app.getAudioManager()

        get("louder") {
            app.sendBroadcast(EventCategory.MEDIA, "louder")

            MediaUtils(audioManager).louder()
            app.sendBroadcast(EventCategory.MEDIA, "Media louder")
            call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")

            call.respondText("Louder", ContentType.Text.JavaScript)
        }

        get("pause") {

            KeyUtils.sendKeyEvent(app, KeyEvent.KEYCODE_MEDIA_PAUSE)
            app.sendBroadcast(EventCategory.MEDIA, "Media pause")

            call.respondText("Pause", ContentType.Text.JavaScript)
        }

    }
}