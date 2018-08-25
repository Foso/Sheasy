package de.jensklingenberg.sheasy.utils

import android.content.Intent
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.model.Event

class ApplicationUtils(val app: App) {


    fun sendBroadcast(event: Event) {
        val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_SHARE).apply {
            putExtra(MySharedMessageBroadcastReceiver.ACTION_SHARE, event)
        }
        app.sendBroadcast(pipp)

    }


    fun sendBroadcast(category: EventCategory, text: String) {
        val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_SHARE).apply {
            putExtra(MySharedMessageBroadcastReceiver.ACTION_SHARE, Event(category, text))
        }
        app.sendBroadcast(pipp)

    }


    @Deprecated("Use EventCategory")
    fun sendBroadcast(category: String, text: String) {
        val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_SHARE).apply {
            putExtra(
                MySharedMessageBroadcastReceiver.ACTION_SHARE,
                Event(EventCategory.DEFAULT, text)
            )
        }
        app.sendBroadcast(pipp)

    }
}