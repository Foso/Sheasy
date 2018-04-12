package de.jensklingenberg.sheasy.enums

/**
 * Created by jens on 8/2/18.
 */
enum class ApiFileCommand {
    APK, FILE, UNKNOWN;


    companion object {
        fun get(str: String): ApiFileCommand {
            for (value in values()) {
                if (value.name.toLowerCase() == str) {
                    return value
                }

            }
            return UNKNOWN


        }

    }


}
