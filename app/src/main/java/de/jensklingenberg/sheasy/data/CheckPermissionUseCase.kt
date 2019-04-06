package de.jensklingenberg.sheasy.data

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class CheckPermissionUseCase(val context: Context) {


    fun checkNotifcationPermission(): Boolean {

        var weHaveNotificationListenerPermission = false
        for (service in NotificationManagerCompat.getEnabledListenerPackages(context)) {
            if (service == context.packageName)
                weHaveNotificationListenerPermission = true
        }
        return weHaveNotificationListenerPermission

    }

    fun requestNotificationPermission() {
        ContextCompat.startActivity(
            context,
            Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS),
            null
        )
    }


}