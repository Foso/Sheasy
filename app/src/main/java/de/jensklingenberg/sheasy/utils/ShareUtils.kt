package de.jensklingenberg.sheasy.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import de.jensklingenberg.sheasy.App


class ShareUtils {


    companion object {

        fun appDownload(context: Context, string: String): String {
            val BASE_PATH =
                NetworkUtils.getIP(context) + ":" + App.port + "/api/v1/file?apk=" + string

            return BASE_PATH
        }


        fun share(activity: Activity, shareT3x: String) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                shareT3x
            )
            sendIntent.type = "text/html"
            activity.startActivity(
                Intent.createChooser(sendIntent, "Share link")
            )

        }
    }
}