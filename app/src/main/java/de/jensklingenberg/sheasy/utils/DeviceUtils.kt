package de.jensklingenberg.sheasy.utils

import android.os.Build
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.model.DeviceResponse
import fi.iki.elonen.NanoHTTPD

/**
 * Created by jens on 25/2/18.
 */
class DeviceUtils {
    companion object {
        public fun getDeviceInfo(): NanoHTTPD.Response? {

            val device = DeviceResponse(
                    Build.MANUFACTURER,
                    Build.MODEL, DiskUtils.busySpace(true),
                    DiskUtils.totalSpace(true),
                    DiskUtils.freeSpace(true),
                    Build.VERSION.RELEASE
            )

            val jsonAdapter =  App.instance.moshi?.adapter(DeviceResponse::class.java)
            return NanoHTTPDExt.debugResponse(jsonAdapter?.toJson(device)?:"")
        }
    }


}