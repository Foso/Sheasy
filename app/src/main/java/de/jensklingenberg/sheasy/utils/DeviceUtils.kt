package de.jensklingenberg.sheasy.utils

import android.os.Build
import de.jensklingenberg.sheasy.model.DeviceResponse

/**
 * Created by jens on 25/2/18.
 */
class DeviceUtils {
    companion object {
        public fun getDeviceInfo(): DeviceResponse {

            val device = DeviceResponse(
                Build.MANUFACTURER,
                Build.MODEL, DiskUtils.busySpace(true),
                DiskUtils.totalSpace(true),
                DiskUtils.freeSpace(true),
                Build.VERSION.RELEASE
            )

            return device
        }
    }


}