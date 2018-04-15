package de.jensklingenberg.sheasy.enums

/**
 * Created by jens on 8/2/18.
 */
enum class ApiFileCommand {
    APK, FILE, INVALID;


    companion object {
        fun get(str: String): ApiFileCommand {
            return values().firstOrNull { it.name.toLowerCase() == str }?: INVALID
        }

    }


}
