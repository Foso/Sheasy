package de.jensklingenberg.sheasy.handler

import android.content.Context
import android.view.KeyEvent
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.enums.MediaCommand
import de.jensklingenberg.sheasy.enums.MediaCommand.*
import de.jensklingenberg.sheasy.utils.extension.getAudioManager
import de.jensklingenberg.sheasy.utils.KeyUtils
import de.jensklingenberg.sheasy.utils.MediaUtils
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 14/2/18.
 */

class MediaRequestHandler(val context: Context, val app: App) {


    fun handle(requestV1: String, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
        when (session.method) {
            NanoHTTPD.Method.GET -> {
                handleGET(requestV1);
            }
        }
        return NanoHTTPD.newFixedLengthResponse("ApiCommand $requestV1 not found")

    }

    private fun handleGET(requestV1: String): NanoHTTPD.Response? {
        val mediaRequest = requestV1.substringAfter(RESOURCE);

        val requestSplitArray = mediaRequest.split("/")
        val command = MediaCommand.get(requestSplitArray.first())
        val audioManager = context.getAudioManager()
        when (command) {
            LOUDER -> {
                MediaUtils(audioManager).louder()
                app.sendBroadcast(EventCategory.MEDIA, "Media louder")
                return NanoHTTPD.newFixedLengthResponse("Media louder")
            }
            LOWER -> {
                MediaUtils(audioManager).lower()
                app.sendBroadcast(EventCategory.MEDIA, "Media lower")
                return NanoHTTPD.newFixedLengthResponse("Audio lower")
            }

            MUTE -> {
                MediaUtils(audioManager).mute()
                app.sendBroadcast(EventCategory.MEDIA, "Media mute")
                return NanoHTTPD.newFixedLengthResponse("Audio mute")
            }

            PREV -> {
                KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS)
                app.sendBroadcast(EventCategory.MEDIA, "Media prev")
                return NanoHTTPD.newFixedLengthResponse("Media prev")
            }

            NEXT -> {
                KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_NEXT)
                app.sendBroadcast(EventCategory.MEDIA, "Media next")
                return NanoHTTPD.newFixedLengthResponse("Media next")
            }


            PLAY -> {
                KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_PLAY)
                app.sendBroadcast("MEDIA", "Media play")
                return NanoHTTPD.newFixedLengthResponse("Media play")
            }

            PAUSE -> {

                KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_PAUSE)
                app.sendBroadcast("MEDIA", "Media pause")

                return NanoHTTPD.newFixedLengthResponse("Media pause")
            }


            MediaCommand.INVALID -> {
                return NanoHTTPD.newFixedLengthResponse("ApiCommand $mediaRequest not found")

            }

        }


    }

    companion object {

        val RESOURCE = "/media/"


    }


}