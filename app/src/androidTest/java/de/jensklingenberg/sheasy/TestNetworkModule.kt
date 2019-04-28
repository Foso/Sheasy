package de.jensklingenberg.sheasy

import dagger.Module
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.di.NetworkModule

class TestNetworkModule(val devicesDataSource: DevicesDataSource): NetworkModule(){


    override fun provideDevicesDataSource(): DevicesDataSource =devicesDataSource

}