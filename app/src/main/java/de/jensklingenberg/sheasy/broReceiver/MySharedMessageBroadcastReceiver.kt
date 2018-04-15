package de.jensklingenberg.sheasy.broReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import de.jensklingenberg.sheasy.interfaces.OnNotificationReceivedListener
import de.jensklingenberg.sheasy.interfaces.NotifyClientEventListener
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.interfaces.ApiEventListener

class MySharedMessageBroadcastReceiver() : BroadcastReceiver() {

    var onNotificationReceivedListener: OnNotificationReceivedListener?=null
    var notifyClientEventListener: NotifyClientEventListener?=null
    var apiEventListener: ApiEventListener?=null

        override fun onReceive(context: Context, intent: Intent) {


            intent.extras?.let {
                when{
                    intent.hasExtra(MESSAGE)->{
                        val test: NotificationResponse = intent.extras.getParcelable(MESSAGE)
                            notifyClientEventListener?.onMessageForClientReceived(test)

                    }

                    intent.hasExtra(ACTION_NOTIFICATION)->{
                        val test: NotificationResponse = intent.extras.getParcelable(ACTION_NOTIFICATION)

                            onNotificationReceivedListener?.onNotificationReceived(test)

                    }

                    intent.hasExtra(ACTION_SHARE)->{
                        val test: Event = intent.extras.getParcelable(ACTION_SHARE)

                        apiEventListener?.onShare(test)

                    }
                    else -> {
                    }
                }
            }


        }

    fun addSharedMessageListener(notifyClientEventListener: NotifyClientEventListener){
        this.notifyClientEventListener=notifyClientEventListener
    }

    fun add(onNotificationReceivedListener: OnNotificationReceivedListener){
        this.onNotificationReceivedListener=onNotificationReceivedListener
    }

    fun addd(apiEventListener: ApiEventListener){
        this.apiEventListener=apiEventListener
    }

    companion object {
        const val MESSAGE = "MESSAGE.ACTION"
        val ACTION_NOTIFICATION = "ACTION_NOTIFICATION"
        val ACTION_SHARE = "seven.notificationlistenerdemo.NLSCONTROL"

    }


    }