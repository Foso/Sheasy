package de.jensklingenberg.sheasy.broReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import de.jensklingenberg.sheasy.interfaces.ApiEventListener
import de.jensklingenberg.sheasy.interfaces.NotifyClientEventListener
import de.jensklingenberg.sheasy.interfaces.OnNotificationReceivedListener
import de.jensklingenberg.sheasy.interfaces.OnScreenShareEventListener
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.NotificationResponse

class MySharedMessageBroadcastReceiver : BroadcastReceiver() {

    var onNotificationReceivedListener: OnNotificationReceivedListener? = null
    var notifyClientEventListener: NotifyClientEventListener? = null
    var apiEventListener: ApiEventListener? = null
    var onScreenShareEventListener: OnScreenShareEventListener? = null


    override fun onReceive(context: Context, intent: Intent) {


        intent.extras?.let {
            when {
                intent.hasExtra(MESSAGE) -> {
                    val test: NotificationResponse = intent.extras.getParcelable(MESSAGE)
                    notifyClientEventListener?.onMessageForClientReceived(test)

                }

                intent.hasExtra(ACTION_NOTIFICATION) -> {
                    val test: NotificationResponse =
                        intent.extras.getParcelable(ACTION_NOTIFICATION)

                    onNotificationReceivedListener?.onNotificationReceived(test)

                }

                intent.hasExtra(ACTION_SHARE) -> {
                    val test: Event = intent.extras.getParcelable(ACTION_SHARE)

                    apiEventListener?.onShare(test)

                }

                intent.hasExtra(EVENT_SCREENSHARE) -> {
                    val test: String = intent.extras.getString(EVENT_SCREENSHARE)

                    onScreenShareEventListener?.onDataForClientReceived(test)

                }
                else -> {
                }
            }
        }


    }


    companion object {
        const val MESSAGE = "MESSAGE.ACTION"
        val ACTION_NOTIFICATION = "ACTION_NOTIFICATION"
        val ACTION_SHARE = "seven.notificationlistenerdemo.NLSCONTROL"
        val EVENT_SCREENSHARE = "SCREENSHARE"

    }


}