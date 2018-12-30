package de.jensklingenberg.sheasy.ui.pairedDevices

import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.model.Device
import repository.SheasyPrefDataSource
import javax.inject.Inject

typealias AboutItem = Pair<String, String>

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
