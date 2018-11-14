package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import fi.iki.elonen.NanoHTTPD
import model.IntentRequest

/**
 * Created by jens on 25/2/18.
 */
class IntentUtils {
    companion object {
        fun startIntent(context: Context, action: IntentRequest?): NanoHTTPD.Response? {
            val sendIntent = Intent()
            sendIntent.action = action?.action //android.Intent.action.SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            sendIntent.type = "text/plain"
            startActivity(context, Intent.createChooser(sendIntent, "dff"), null)
            return NanoHTTPD.newFixedLengthResponse("Intent send")

        }


    }
}