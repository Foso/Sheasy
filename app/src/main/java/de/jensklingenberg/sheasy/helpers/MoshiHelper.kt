package de.jensklingenberg.sheasy.helpers

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.model.ContactResponse
import java.lang.reflect.ParameterizedType

class MoshiHelper{
    companion object {
        fun contactsToJson(moshi: Moshi?, list: List<ContactResponse>):String{
            moshi?.let {
                val listMyData: ParameterizedType = Types.newParameterizedType(List::class.java, ContactResponse::class.java)
                val adapter = moshi?.adapter<kotlin.collections.List<ContactResponse>>(listMyData)
                return adapter?.toJson(list)?:""
            }

            return ""

        }

        fun appsToJson(moshi: Moshi?, list: List<AppsResponse>): String {
            moshi?.let {
                val listMyData: ParameterizedType = Types.newParameterizedType(List::class.java, AppsResponse::class.java)
                val adapter = moshi.adapter<kotlin.collections.List<AppsResponse>>(listMyData)
                return adapter?.toJson(list)?:""
            }

            return ""
        }


    }
}