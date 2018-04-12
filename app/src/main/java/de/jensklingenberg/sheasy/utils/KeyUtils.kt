package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.view.KeyEvent

/**
 * Created by jens on 24/2/18.
 */

class KeyUtils {

    companion object {
        fun sendKeyEvent(context: Context, keyevent: Int) {
            val eventtime = SystemClock.uptimeMillis()
            val downIntent = Intent(Intent.ACTION_MEDIA_BUTTON, null)
            val downEvent = KeyEvent(eventtime, eventtime,
                    KeyEvent.ACTION_DOWN, keyevent, 0)
            downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent)
            context.sendOrderedBroadcast(downIntent, null)

            val upIntent = Intent(Intent.ACTION_MEDIA_BUTTON, null)
            val upEvent = KeyEvent(eventtime, eventtime,
                    KeyEvent.ACTION_UP, keyevent, 0)
            upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent)
            context.sendOrderedBroadcast(upIntent, null)
        }
    }

}