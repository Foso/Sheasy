package de.jensklingenberg.sheasy.extension


import de.jensklingenberg.sheasy.BuildConfig
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 30/3/18.
 */

fun NanoHTTPD.IHTTPSession.getParameterQueryMap(): HashMap<String, String> {
    val queryMap = HashMap<String, String>()

    val query  =this.queryParameterString
    val split = query.split("&")
    split.forEach {
        val querySplit = it.split("=")
        var parameter= querySplit[0]
        var value = querySplit[1]
        queryMap.put(parameter, value)

    }

    return queryMap
}


class NanoHTTPDExt{
    companion object {
        fun debugResponse(string:String): NanoHTTPD.Response? {

            val newFixedLengthResponse1 = NanoHTTPD.newFixedLengthResponse(string)
            if(BuildConfig.DEBUG){
                newFixedLengthResponse1.addHeader("Access-Control-Allow-Origin", "*")
            }
            return newFixedLengthResponse1
        }
    }
}