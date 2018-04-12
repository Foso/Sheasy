package de.jensklingenberg.sheasy.handler

import android.content.Context
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.model.ContactResponse
import de.jensklingenberg.sheasy.utils.ContactUtils
import fi.iki.elonen.NanoHTTPD
import java.lang.reflect.ParameterizedType

/**
 * Created by jens on 14/2/18.
 */

class ContactsRequestHandler {


    companion object {

        val RESOURCE = "/contacts/"
        val ACTION = "Contacts REQUESTED"


        fun handle(context: Context,requestV1: String): NanoHTTPD.Response? {
            val contacts = ContactUtils.readContacts(context.contentResolver)

            val listMyData: ParameterizedType = Types.newParameterizedType(List::class.java, ContactResponse::class.java)
            val adapter = App.instance.moshi?.adapter<kotlin.collections.List<ContactResponse>>(listMyData)
            App.instance.sendBroadcast(ACTION, requestV1.substringAfter(RESOURCE))

            return NanoHTTPDExt.debugResponse(adapter?.toJson(contacts)?:"")

        }


    }


}


