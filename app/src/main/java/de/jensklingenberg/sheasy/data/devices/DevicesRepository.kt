package de.jensklingenberg.sheasy.data.devices

import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.model.AuthorizationType
import de.jensklingenberg.sheasy.model.Device
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class DevicesRepository : DevicesDataSource {

    val knownDevices: BehaviorSubject<List<Device>> = BehaviorSubject.create()
    override val auth = mutableListOf<Device>()


    override fun getAuthorizedDevices(): Observable<List<Device>> {
        return knownDevices.hide()
    }

    override fun addAuthorizedDevice(device: Device) {
        auth.add(device)
        knownDevices.onNext(auth)

    }

    override fun removeDevice(device: Device) {
        val index = auth.indexOfFirst { knownDevice -> knownDevice.ip.equals(device.ip) }
        auth[index] = device.copy(authorizationType = AuthorizationType.REVOKED)
        knownDevices.onNext(auth)



    }

}