package de.jensklingenberg.sheasy.handler

import android.content.Context
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.utils.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.utils.ContactUtils
import de.jensklingenberg.sheasy.utils.extension.contactsToJson
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 14/2/18.
 */

class ContactsRequestHandler(val context: Context, val app: App, val moshi: Moshi) {


    fun handle(requestV1: String): NanoHTTPD.Response? {
        val contacts = ContactUtils.readContacts(context.contentResolver)
        app.sendBroadcast(EventCategory.REQUEST, ACTION)
        val response = moshi.contactsToJson(contacts)

        return NanoHTTPDExt.debugResponse(response)
    }


    companion object {

        val RESOURCE = "/contacts/"
        val ACTION = "Contacts REQUESTED"


    }


}


