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

class ContactsRequestHandler(val context: Context) {


    fun handle(requestV1: String): NanoHTTPD.Response? {
        val contacts = ContactUtils.readContacts(context.contentResolver)


        App.instance.sendBroadcast(ACTION, requestV1.substringAfter(RESOURCE))
        val moshi=App.instance.moshi
        val response = MoshiHelper.toJson(moshi,contacts)

        return NanoHTTPDExt.debugResponse(response)
    }


    companion object {

        val RESOURCE = "/contacts/"
        val ACTION = "Contacts REQUESTED"





    }


}


