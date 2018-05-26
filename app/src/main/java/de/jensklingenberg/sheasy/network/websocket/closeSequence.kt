package de.jensklingenberg.sheasy.network.websocket

import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readReason
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.withTimeoutOrNull
import org.threeten.bp.Duration
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Starts websocket close sequence actor job on [coroutineContext] for websocket [session]
 * A close sequence job is listening for close events. If a client initiates close then the job is replying to client's
 * close and quits. If the [session] is terminating (e.g. the server is going down) then the job is sending close frame
 * and waiting for client's reply (up to timeout provided by [timeout] function) and then quits.
 *
 * Just before do quit the job calls [populateCloseReason] with last known close reason however it may
 * provide `null` reason it get cancelled.
 *
 * Once the job is completed, the connection could be terminated.
 */
fun closeSequence(
    coroutineContext: CoroutineContext,
    session: WebSocketSession,
    timeout: () -> Duration,
    populateCloseReason: (reason: CloseReason?) -> Unit
): SendChannel<CloseFrameEvent> {
    return actor(coroutineContext, capacity = 2, start = CoroutineStart.LAZY) {
        var reason: CloseReason? = null

        try {
            val firstCloseEvent = receiveOrNull() ?: return@actor

            withTimeoutOrNull(timeout().toMillis(), TimeUnit.MILLISECONDS) {
                reason = firstCloseEvent.frame.readReason()
                when (firstCloseEvent) {
                    is CloseFrameEvent.ToSend -> {
                        session.send(firstCloseEvent.frame)

                        while (true) {
                            val event = receiveOrNull() ?: break
                            if (event !is CloseFrameEvent.ToSend) break
                        }
                    }

                    is CloseFrameEvent.Received -> {
                        session.send(
                            Frame.Close(
                                reason ?: CloseReason(
                                    CloseReason.Codes.NORMAL,
                                    "OK"
                                )
                            )
                        )
                        session.flush()
                    }
                }
            }
        } finally {
            // terminate connection in any case
            populateCloseReason(reason)
        }
    }
}

/**
 * A close event: a frame received or to be sent
 * @see [closeSequence]
 */
sealed class CloseFrameEvent(val frame: Frame.Close) {
    class Received(frame: Frame.Close) : CloseFrameEvent(frame)
    class ToSend(frame: Frame.Close) : CloseFrameEvent(frame)
}