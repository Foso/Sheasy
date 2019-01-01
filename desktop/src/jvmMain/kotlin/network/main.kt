package network

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.origin
import io.ktor.gson.gson
import io.ktor.http.ContentDisposition
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.flattenForEach
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import kotlinx.coroutines.launch
import main.MockTestDataSource
import de.jensklingenberg.sheasy.web.model.KtorApplicationCall
import de.jensklingenberg.sheasy.web.model.Resource
import de.jensklingenberg.sheasy.web.model.Response
import de.jensklingenberg.sheasy.web.model.checkState
import network.ktor.DesktopFileRouteHandler
import network.routehandler.FileRouteHandler
import network.routes.apps
import repository.DesktopSheasyPrefDataSource
import repository.SheasyPrefDataSource
import java.time.Duration


val ErrorNotAuthorized = Response.error("NOT AUTHORZIED", "")


fun main() {
val sheasyPref : SheasyPrefDataSource = DesktopSheasyPrefDataSource()
val fileRouteHandler : FileRouteHandler = DesktopFileRouteHandler()


    val server = embeddedServer(Netty, port = sheasyPref.port) {


        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(60) // Disabled (null) by default
            timeout = Duration.ofSeconds(15)
            maxFrameSize =
                Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
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
            route(sheasyPref.APIV1) {
                apps()
                route("/file") {
                    param("shared") {
                        get {
                            val filePath = call.parameters["shared"] ?: ""
                            val toTo = call.toTo("shared").apply {

                                parameter=filePath
                            }
                            val resource= fileRouteHandler.getShared( toTo)
                            resource.checkState(onError = {
                                launch {
                                    call.response.header(
                                        HttpHeaders.AccessControlAllowOrigin,
                                        "*"
                                    )
                                    call.respond(resource)
                                }
                            },onSuccess = {
                                launch {
                                    call.response.header(
                                        HttpHeaders.AccessControlAllowOrigin,
                                        "*"
                                    )
                                    call.respond(resource)
                                }
                            })
                        }
                    }



                     }
            }
        }
    }
    server.start(wait = true)
}


public fun ApplicationCall.toTo(apiPath:String=""): KtorApplicationCall {


    return KtorApplicationCall(
        remoteHostIp = this.request.origin.remoteHost,
        requestedApiPath = apiPath


    )


}

