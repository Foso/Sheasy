package de.jensklingenberg.sheasy.handler

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.ContactResponse
import java.lang.reflect.ParameterizedType

class MoshiHelper{
    companion object {
        fun toJson(moshi: Moshi?,list: List<ContactResponse>):String{
            moshi?.let {
                val listMyData: ParameterizedType = Types.newParameterizedType(List::class.java, ContactResponse::class.java)
                val adapter = moshi?.adapter<kotlin.collections.List<ContactResponse>>(listMyData)
                return adapter?.toJson(list)?:""
            }

            return ""

        }
    }
}