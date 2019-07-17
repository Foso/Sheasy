package de.jensklingenberg.sheasy.data.devices

import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.model.AuthorizationType
import de.jensklingenberg.sheasy.model.Device
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class DevicesRepository : DevicesDataSource {

    val knownDevices: BehaviorSubject<List<Device>> = BehaviorSubject.create()
    override val auth = mutableListOf<Device>()


    override fun getDevices(): Observable<List<Device>> {
        return knownDevices.hide()
    }

    override fun addAuthorizedDevice(device: Device) {
        val index = auth.indexOfFirst { knownDevice -> knownDevice.ip == device.ip }

        val dev = device.copy(authorizationType = AuthorizationType.AUTHORIZED)
        if (index == -1) {
            auth.add(dev)
        } else {
            auth[index] = dev

        }
        knownDevices.onNext(auth)

    }

    override fun removeDevice(device: Device) {
        val index = auth.indexOfFirst { knownDevice -> knownDevice.ip == device.ip }
        auth[index] = device.copy(authorizationType = AuthorizationType.UNAUTH)
        knownDevices.onNext(auth)


    }

}