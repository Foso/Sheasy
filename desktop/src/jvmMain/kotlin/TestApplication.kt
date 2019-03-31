package network

import de.jensklingenberg.sheasy.network.routehandler.KtorApiHandler
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import network.ktor.DesktopFileRouteHandler
import network.ktor.routes.DesktopKtorApiHandler
import network.ktor.repository.DesktopSheasyPrefDataSource
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.ktor.features.CORS
import io.ktor.features.PartialContent
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.routing.post
import network.network.ktor.repository.FileRepository
import java.time.Duration


fun main() {
    val fileDataSource = FileRepository()
    val sheasyPref : SheasyPrefDataSource = DesktopSheasyPrefDataSource()
    val fileRouteHandler : FileRouteHandler = DesktopFileRouteHandler(fileDataSource)
    val ktorApiHandler: KtorApiHandler = DesktopKtorApiHandler()



    val server = embeddedServer(Netty, port = sheasyPref.httpPort) {


        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        install(PartialContent) {
            maxRangeCount = 10
        }

        install(CORS) {
            anyHost()
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            listOf(
                HttpMethod.Get,
                HttpMethod.Put,
                HttpMethod.Delete,
                HttpMethod.Options
            ).forEach { method(it) }
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

            post("/hallo"){
                call.respondText("Hello World!", ContentType.Text.Plain)

            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
            route("/api/v1/") {
                ktorApiHandler.run {
                    file(fileRouteHandler)
                }
            }
        }
    }
    server.start(wait = true)
}


