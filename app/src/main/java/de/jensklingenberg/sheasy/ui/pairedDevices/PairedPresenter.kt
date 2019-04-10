package de.jensklingenberg.sheasy.ui.pairedDevices

import android.content.Context
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.web.model.Device
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class PairedPresenter(val view: PairedContract.View) : PairedContract.Presenter {

    @Inject
    lateinit var context: Context

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onMoreButtonClicked(view: View, payload: Any) {
        val device = payload as? Device

        device?.let {
          this.view.showContextMenu(device,view)
        }


        //this.view.onMoreButtonClicked(view,payload)
    }





    override fun onCreate() {
        loadPairedDevices()

    }

    private fun loadPairedDevices() {
        sheasyPrefDataSource.devicesRepository.authorizedDevices
            .map {
                DeviceListItemSourceItem(it, this)
            }.run {
                view.setData(this)
            }
    }




    override fun revokeDevice(device: Device) {
        sheasyPrefDataSource.devicesRepository.authorizedDevices.remove(device)
        loadPairedDevices()

    }

    override fun onDestroy() {
        compositeDisposable.dispose()

    }


}
