package de.jensklingenberg.sheasy.data

import de.jensklingenberg.sheasy.model.Device
import io.reactivex.Observable

interface DevicesDataSource {
    //   val authorizedDevices: MutableList<Device>
    fun getAuthorizedDevices(): Observable<List<Device>>

    fun addAuthorizedDevice(device: Device)
    fun removeDevice(device: Device)


    val authorizedDevices: MutableList<Device>
}
