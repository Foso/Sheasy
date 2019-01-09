package de.jensklingenberg.sheasy.network.devices

import de.jensklingenberg.sheasy.web.model.Device

interface DevicesDataSource {
    val authorizedDevices: MutableList<Device>

    fun addAuthorizedDevice(device: Device)
    fun removeDevice(device: Device)
}
