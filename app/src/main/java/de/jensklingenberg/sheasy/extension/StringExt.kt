package de.jensklingenberg.sheasy.extension

/**
 * Created by jens on 30/3/18.
 */

public fun String.remove(string: String): String {
    return this.replace(string, "")
}