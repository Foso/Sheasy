package de.jensklingenberg.sheasy.enums

/**
 * Created by jens on 8/2/18.
 */
enum class ApiCommand {
    media, INVALID, Apps, Download, Intent, DEVICE, WEB, FILE, SHARE, CONTACTS;


    companion object {
        fun get(str: String): ApiCommand {
            return values().firstOrNull { it.name.toLowerCase() == str } ?: INVALID
        }

    }


}
