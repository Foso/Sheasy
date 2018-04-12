package de.jensklingenberg.sheasy.handler

import android.content.Context
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.model.IntentRequest
import de.jensklingenberg.sheasy.utils.IntentUtils
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 14/2/18.
 */

class IntentRequestHandler {


    companion object {

        val RESOURCE = "/Intent/"

        fun handle(context: Context, session: NanoHTTPD.IHTTPSession) :NanoHTTPD.Response? {

            when (session.method) {
                NanoHTTPD.Method.POST -> {
                   handlePOST(context,session)
                }
            }

            return NanoHTTPD.newFixedLengthResponse("IntentCommand not found")

        }

        fun handlePOST(context: Context,session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
            var map = HashMap<String, String>()
            session.parseBody(map)


            val jsonAdapter = Moshi.Builder()
                    .build().adapter(IntentRequest::class.java)
            val get1 = jsonAdapter.fromJson(map[("postData")])


            return IntentUtils.startIntent(context, get1)
        }


    }


}