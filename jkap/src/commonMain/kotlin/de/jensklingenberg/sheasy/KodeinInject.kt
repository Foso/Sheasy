package de.jensklingenberg.sheasy.web

import kotlin.reflect.KClass

annotation class KodeinInject
annotation class KodeinModul( val modules: Array<KClass<*>> = arrayOf())
annotation class Provides