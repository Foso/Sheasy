package de.jensklingenberg.sheasy.utils.extension

import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.media.AudioManager
import android.media.projection.MediaProjectionManager
import android.net.wifi.WifiManager
import java.io.File
import java.io.InputStream

/**
 * Created by jens on 30/3/18.
 */

fun Context.audioManager() = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager

fun Context.clipboardManager() =
    this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

fun Context.notificationManager() =
    this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun Context.mediaProjectionManager() =
    this.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

fun Context.wifiManager(): WifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager


fun InputStream.toFile(path: String) {
    use { input ->
        File(path).outputStream().use { input.copyTo(it) }
    }
}

