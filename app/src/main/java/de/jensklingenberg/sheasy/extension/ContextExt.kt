package de.jensklingenberg.sheasy.extension

import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.media.AudioManager
import java.io.File
import java.io.InputStream

/**
 * Created by jens on 30/3/18.
 */

public fun Context.getAudioManager(): AudioManager {
    return this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
}

public fun Context.getClipboardMangaer(): ClipboardManager {
    return this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}

public fun Context.notifcationManager(): NotificationManager {
    return this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}

fun InputStream.toFile(path: String) {
    use { input ->
        File(path).outputStream().use { input.copyTo(it) }
    }
}

