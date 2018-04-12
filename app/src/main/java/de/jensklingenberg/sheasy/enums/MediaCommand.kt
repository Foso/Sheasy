package de.jensklingenberg.sheasy.enums

/**
 * Created by jens on 8/2/18.
 */
enum class MediaCommand {
    LOUDER, LOWER, MUTE, PREV, NEXT, PLAY, PAUSE, UNKNOWN;


    companion object {
        fun get(str: String): MediaCommand {
            for (value in values()) {
                if (value.name.toLowerCase() == str.substringBefore("/")) {
                    return value
                }

            }
            return UNKNOWN


            throw  RuntimeException(
                    "there is no action that matches the action " + " + make sure your using types correctly");

        }

    }


}
