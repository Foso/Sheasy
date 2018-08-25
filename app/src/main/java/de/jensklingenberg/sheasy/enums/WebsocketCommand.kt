package de.jensklingenberg.sheasy.enums

/**
 * Created by jens on 8/2/18.
 */
enum class WebsocketCommand {
    NOTIFICATION, MESSAGE, INVALID, SCREENSHARE;


    companion object {
        fun get(str: String): WebsocketCommand {
            return values().firstOrNull { it.name.toLowerCase() == str.substringBefore("/") }
                    ?: INVALID


        }

    }


}
