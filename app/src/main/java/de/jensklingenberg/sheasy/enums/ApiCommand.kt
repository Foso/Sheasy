package de.jensklingenberg.sheasy.enums

/**
 * Created by jens on 8/2/18.
 */
enum class ApiCommand {
    media, other, Apps, Download, Intent, DEVICE, WEB, FILE, SHARE,CONTACTS;


    companion object {
        fun get(str: String): ApiCommand {
            for (value in values()) {
                if (value.name.toLowerCase() == str) {
                    return value
                }

            }
            return other


            throw  RuntimeException(
                    "there is no action that matches the action " + " + make sure your using types correctly");

        }

    }


}
