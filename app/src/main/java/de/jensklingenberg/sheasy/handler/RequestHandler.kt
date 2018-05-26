package de.jensklingenberg.sheasy.handler

import android.content.Context
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.ApiCommand
import de.jensklingenberg.sheasy.utils.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.utils.extension.remove
import fi.iki.elonen.NanoHTTPD

class RequestHandlerFactory {


    companion object {
        fun create(ctx: Context, session1: NanoHTTPD.IHTTPSession, app: App): NanoHTTPD.Response? {
            var uri = session1.uri

            val API_V1 = "api/v1"

            if (uri.startsWith("/${API_V1}/")) {
                uri = uri.remove("/${API_V1}/")
                val split = uri.split("/")
                val apiCommand = ApiCommand.get(split.first())

                when (apiCommand) {

                    ApiCommand.Apps -> {
                        val response = AppsRequestHandler(app, app.moshi).handle(session1.uri)
                        return NanoHTTPDExt.debugResponse(response)

                    }
                    ApiCommand.media -> {
                        return MediaRequestHandler(ctx, app).handle(session1.uri, session1)
                    }
                    ApiCommand.Intent -> {
                        return IntentRequestHandler.handle(ctx, session1)
                    }
                    ApiCommand.DEVICE -> {
                        return DeviceRequestHandler.handle(ctx, session1.uri)
                    }
                    ApiCommand.WEB -> {
                        return WebRequestHandler.handle(ctx, session1.uri)
                    }
                    ApiCommand.Download -> {
                        return WebRequestHandler.handle(ctx, session1.uri)
                    }
                    ApiCommand.FILE -> {
                        return FileRequestHandler.handleRequest(session1)
                    }
                    ApiCommand.SHARE -> {
                        return ShareRequestHandler.handle(session1.uri)
                    }

                    ApiCommand.CONTACTS -> {
                        return ContactsRequestHandler(ctx, app, app.moshi).handle(session1.uri)
                    }
                }
            } else {
                uri = uri.replaceFirst("/", "", true)
                return WebRequestHandler.handle(ctx, uri)


            }

            return NanoHTTPD.newFixedLengthResponse("Command ${uri} not found")
        }
    }
}
