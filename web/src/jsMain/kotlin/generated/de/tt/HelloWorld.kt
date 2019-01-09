package de.tt

import kotlin.String

class Greeter(val name: String) {
    fun greet() {
        println()
    }
}

fun main(vararg args: String) {
    Greeter(args[0]).greet()
}
