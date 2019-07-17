package de.jensklingenberg.sheasy.ui.pairedDevices

import android.content.Context
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.jakewharton.rxbinding3.appcompat.itemClicks
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.model.AuthorizationType
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.ui.common.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class PairedPresenter(val view: PairedContract.View) : PairedContract.Presenter {

    @Inject
    lateinit var context: Context

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var devicesDataSource: DevicesDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onCreate() {
        loadPairedDevices()

    }

    private fun loadPairedDevices() {

        devicesDataSource.getDevices()
            .map { devices ->

                val list = arrayListOf<BaseDataSourceItem<*>>()


                val autho = devices.filter { it.authorizationType == AuthorizationType.AUTHORIZED }

                val revoked = devices.filter { it.authorizationType == AuthorizationType.UNAUTH }

                list.apply {

                    if (revoked.isNotEmpty()) {
                        add(
                            GenericListHeaderSourceItem(
                                "Unauthorized"
                            )
                        )
                        addAll(devices.map {
                            DeviceListItemSourceItem(
                                it
                            ) { view, item, device -> setupUnathorizedContextMenu(view, item, device) }
                        })
                    }

                    if (autho.isNotEmpty()) {
                        add(
                            GenericListHeaderSourceItem(
                                "Authorized"
                            )
                        )
                        addAll(devices.map {
                            DeviceListItemSourceItem(
                                it
                            ) { view, item, device -> setupContextMenu(view, item, device) }
                        })
                    }

                }


            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                view.setData(it)
            }.addTo(compositeDisposable)


    }


    override fun revokeDevice(device: Device) {
        devicesDataSource.removeDevice(device)
    }


    fun authorizeDevice(device: Device) {
        devicesDataSource.addAuthorizedDevice(device)
    }

    fun setupContextMenu(
        it: View,
        item: DeviceListItemSourceItem,
        device: Device
    ) {
        PopupMenu(it.context, it)
            .apply {
                if (device.authorizationType == AuthorizationType.AUTHORIZED) {
                    menuInflater
                        .inflate(R.menu.paired_devices_actions, menu)
                }
            }.also {
                it.itemClicks()
                    .doOnNext { menuItem ->

                        onContextMenuClick(
                            device,
                            menuItem.itemId
                        )

                    }.subscribe()
            }.show()
    }

    fun setupUnathorizedContextMenu(
        it: View,
        item: DeviceListItemSourceItem,
        device: Device
    ) {
        PopupMenu(it.context, it)
            .apply {
                if (device.authorizationType == AuthorizationType.UNAUTH) {
                    menuInflater
                        .inflate(R.menu.revoked_devices_actions, menu)
                }
            }.also {
                it.itemClicks()
                    .doOnNext { menuItem ->

                        onContextMenuClick(
                            device,
                            menuItem.itemId
                        )

                    }.subscribe()
            }.show()
    }


    fun onContextMenuClick(device: Device, id: Int) {
        when (id) {
            R.id.menu_revoke -> {
                revokeDevice(device)
                true
            }
            R.id.menu_authorize -> {
                authorizeDevice(device)
                true
            }
            else -> {
                true
            }
        }

    }

    override fun onDestroy() {
        compositeDisposable.dispose()

    }

}
