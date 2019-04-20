package de.jensklingenberg.sheasy.data.usecase

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class CheckPermissionUseCase(val context: Context) {


    fun checkNotificationPermission(): Boolean {

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
            Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }


}