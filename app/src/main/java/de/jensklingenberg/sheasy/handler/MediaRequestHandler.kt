package de.jensklingenberg.sheasy.handler

import android.content.Context
import android.view.KeyEvent
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.MediaCommand
import de.jensklingenberg.sheasy.enums.MediaCommand.*
import de.jensklingenberg.sheasy.extension.getAudioManager
import de.jensklingenberg.sheasy.utils.KeyUtils
import de.jensklingenberg.sheasy.utils.MediatUtils
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 14/2/18.
 */

class MediaRequestHandler {


    companion object {

        val RESOURCE = "/media/"
        val CATEGORY = "MEDIA"


        fun handle(context: Context, requestV1: String,session:NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
            when(session.method){
                NanoHTTPD.Method.GET->{
                    handleGET(context,requestV1);
                }
            }
            return NanoHTTPD.newFixedLengthResponse("ApiCommand $requestV1 not found")

            }

        private fun handleGET(context: Context, requestV1: String): NanoHTTPD.Response? {
            val mediaRequest = requestV1.substringAfter(RESOURCE);

            val requestSplitArray = mediaRequest.split("/")
            val command = MediaCommand.get(requestSplitArray.first())

            when (command) {
                LOUDER -> {
                    MediatUtils(context.getAudioManager()).louder()
                    App.instance.sendBroadcast(CATEGORY, "Media louder")
                    return NanoHTTPD.newFixedLengthResponse("Media louder")
                }
                LOWER -> {
                    MediatUtils(context.getAudioManager()).lower()
                    App.instance.sendBroadcast(CATEGORY, "Media lower")
                    return NanoHTTPD.newFixedLengthResponse("Audio lower")
                }

                MUTE -> {
                    MediatUtils(context.getAudioManager()).mute()
                    App.instance.sendBroadcast(CATEGORY, "Media mute")
                    return NanoHTTPD.newFixedLengthResponse("Audio mute")
                }

                PREV -> {
                    KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS)
                    App.instance.sendBroadcast(CATEGORY, "Media prev")
                    return NanoHTTPD.newFixedLengthResponse("Media prev")
                }

                NEXT -> {
                    KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_NEXT)
                    App.instance.sendBroadcast(CATEGORY, "Media next")
                    return NanoHTTPD.newFixedLengthResponse("Media next")
                }


                PLAY -> {
                    KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_PLAY)
                    App.instance.sendBroadcast("MEDIA", "Media play")
                    return NanoHTTPD.newFixedLengthResponse("Media play")
                }

                PAUSE -> {

                    KeyUtils.sendKeyEvent(context, KeyEvent.KEYCODE_MEDIA_PAUSE)
                    App.instance.sendBroadcast("MEDIA", "Media pause")

                    return NanoHTTPD.newFixedLengthResponse("Media pause")
                }


                MediaCommand.UNKNOWN -> {
                    return NanoHTTPD.newFixedLengthResponse("ApiCommand $mediaRequest not found")

                }

        }

        return NanoHTTPD.newFixedLengthResponse("ApiCommand $mediaRequest not found")

        }


    }


}