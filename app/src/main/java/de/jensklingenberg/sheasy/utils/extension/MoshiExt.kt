package de.jensklingenberg.sheasy.utils.extension

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType


fun Moshi.toJson(list: List<Any>): String {

    val myData: ParameterizedType =
        Types.newParameterizedType(List::class.java, Any::class.java)
    val adapter = this.adapter<kotlin.collections.List<Any>>(myData)
    return adapter?.toJson(list) ?: ""
}

fun Moshi.toJson(any: Any): String {

    val myData: ParameterizedType =
        Types.newParameterizedType(Any::class.java)
    val adapter = this.adapter<Any>(myData)
    return adapter?.toJson(any) ?: ""
}


