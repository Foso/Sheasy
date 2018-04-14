package de.jensklingenberg.sheasy.handler

import android.content.Context
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.ResponseFile
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 14/2/18.
 */

class WebRequestHandler {


    companion object {

        val MEDIA = "/web/"

        fun handle(context: Context, requestV1: String): NanoHTTPD.Response? {
            return when {
                requestV1.isEmpty() -> {
                    val returnAssetFile: ResponseFile = FUtils.returnAssetFile(context, "web/index.html")
                    when(returnAssetFile.fileInputStream){
                        null->{
                            return NanoHTTPD.newFixedLengthResponse("File $requestV1 not found")

                        }else->{
                        return NanoHTTPDExt.newChunkedResponse(returnAssetFile)

                    }
                    }

                }
                else -> {
                    val returnAssetFile = FUtils.returnAssetFile(context, requestV1)
                    return NanoHTTPDExt.newChunkedResponse(returnAssetFile)

                }
            }


        }


    }


}

