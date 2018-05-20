package de.jensklingenberg.sheasy

import io.ktor.application.Application
import io.ktor.application.ApplicationFeature
import io.ktor.util.AttributeKey
import org.threeten.bp.Duration

/**
 * WebSockets support feature. It is required to be installed first before binding any websocket endpoints
 *
 * ```
 * install(WebSockets)
 *
 * install(Routing) {
 *     webSocket("/ws") {
 *          incoming.consumeForEach { ... }
 *     }
 * }
 * ```
 */
class BackportWebSocket(
    val pingInterval: Duration?,
    val timeout: Duration,
    val maxFrameSize: Long,
    val masking: Boolean
) {
    class WebSocketOptions {
        var pingPeriod: Duration? = null
        var timeout: Duration = Duration.ofSeconds(15)
        var maxFrameSize = Long.MAX_VALUE
        var masking: Boolean = false
    }

    companion object Feature :
        ApplicationFeature<Application, WebSocketOptions, BackportWebSocket> {
        override val key = AttributeKey<BackportWebSocket>("WebSockets")

        override fun install(
            pipeline: Application,
            configure: WebSocketOptions.() -> Unit
        ): BackportWebSocket {
            return WebSocketOptions().also(configure).let { options ->
                val webSockets = BackportWebSocket(
                    options.pingPeriod,
                    options.timeout,
                    options.maxFrameSize,
                    options.masking
                )

                webSockets
            }
        }
    }
}