package de.jensklingenberg.sheasy.data

import de.jensklingenberg.sheasy.model.Device
import io.reactivex.Observable

interface DevicesDataSource {
    //   val auth: MutableList<Device>
    fun getDevices(): Observable<List<Device>>

    fun addAuthorizedDevice(device: Device)
    fun removeDevice(device: Device)


    val auth: MutableList<Device>
}
