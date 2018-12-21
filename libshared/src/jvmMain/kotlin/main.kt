package main

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import model.Response
import java.time.Duration


fun main(args: Array<String>) {


    val APIV1="/api/v1/"

    val server = embeddedServer(Netty, port = 8766) {


        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(60) // Disabled (null) by default
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
            masking = false
        }


        routing {
//http://192.168.178.20:8766/api/v1/file?shared=/storage/emulated/0/Music
            webSocket("/echo") {
                val frame = incoming.receive()
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        outgoing.send(Frame.Text(text))
                        if (text.equals("bye", ignoreCase = true)) {
                            close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                        }
                    }
                }
            }

            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
            route(APIV1){
               apps()
            }
        }
    }
    server.start(wait = true)
}

private fun Route.apps() {
    get("/apps"){
        call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
        call.respond(Response.error("NOT AUTHORZIED",""))
    }

}
