package de.jensklingenberg.sheasy.utils.extension

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.model.ContactResponse
import java.lang.reflect.ParameterizedType


fun Moshi.contactsToJson(list: List<ContactResponse>): String {

    val listMyData: ParameterizedType =
        Types.newParameterizedType(List::class.java, ContactResponse::class.java)
    val adapter = this?.adapter<kotlin.collections.List<ContactResponse>>(listMyData)
    return adapter?.toJson(list) ?: ""


    return ""

}

fun Moshi.appsToJson(list: List<AppsResponse>): String {

    val listMyData: ParameterizedType =
        Types.newParameterizedType(List::class.java, AppsResponse::class.java)
    val adapter = this.adapter<kotlin.collections.List<AppsResponse>>(listMyData)
    return adapter?.toJson(list) ?: ""


    return ""
}


fun Moshi.listToJSON(list: List<Any>): String {

    val listMyData: ParameterizedType =
        Types.newParameterizedType(List::class.java, Any::class.java)
    val adapter = this.adapter<kotlin.collections.List<Any>>(listMyData)
    return adapter?.toJson(list) ?: ""


    return ""
}

fun Moshi.toJSON(any: Any): String {

    val listMyData: ParameterizedType =
        Types.newParameterizedType(Any::class.java)
    val adapter = this.adapter<Any>(listMyData)
    return adapter?.toJson(any) ?: ""


    return ""
}


