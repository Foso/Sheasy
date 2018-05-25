package de.jensklingenberg.sheasy.enums

/**
 * Created by jens on 8/2/18.
 */
enum class MediaCommand {
    LOUDER, LOWER, MUTE, PREV, NEXT, PLAY, PAUSE, INVALID;


    companion object {
        fun get(str: String): MediaCommand {


            return values().firstOrNull { it.name.toLowerCase() == str.substringBefore("/") }
                    ?: INVALID


        }

    }


}
