package de.jensklingenberg.sheasy.ui.pairedDevices

import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.web.model.Device
import javax.inject.Inject


class PairedViewModel : ViewModel() {

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    fun loadApps(): List<Device> {
        return sheasyPrefDataSource.authorizedDevices.apply {
            if (isEmpty()) {
                add(Device("No connected device"))
            }
        }
    }


}
