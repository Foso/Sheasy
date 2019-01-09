package de.jensklingenberg.sheasy.data.devices

import de.jensklingenberg.sheasy.network.devices.DevicesDataSource
import de.jensklingenberg.sheasy.web.model.Device

class DevicesRepository: DevicesDataSource {

    override val authorizedDevices = mutableListOf<Device>()


    override fun addAuthorizedDevice(device: Device) {
        authorizedDevices.add(device)
    }

    override fun removeDevice(device: Device) {
        authorizedDevices.remove(device)
    }

}