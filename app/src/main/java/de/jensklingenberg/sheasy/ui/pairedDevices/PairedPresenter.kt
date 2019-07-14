package de.jensklingenberg.sheasy.ui.pairedDevices

import android.content.Context
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

        devicesDataSource.getAuthorizedDevices()
            .map { devices ->

                val list = arrayListOf<BaseDataSourceItem<*>>()


                val autho = devices.filter { it.authorizationType == AuthorizationType.AUTHORIZED }

                val revoked = devices.filter { it.authorizationType == AuthorizationType.REVOKED }

                list.apply {

                    if (revoked.isNotEmpty()) {
                        add(
                            GenericListHeaderSourceItem(
                                "Revoked"
                            )
                        )
                        addAll(devices.map { DeviceListItemSourceItem(it, this@PairedPresenter) })
                    }

                    if (autho.isNotEmpty()) {
                        add(
                            GenericListHeaderSourceItem(
                                "Authorized"
                            )
                        )
                        addAll(devices.map { DeviceListItemSourceItem(it, this@PairedPresenter) })
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

    override fun onDestroy() {
        compositeDisposable.dispose()

    }

    fun authorizeDevice(device: Device) {
        devicesDataSource.addAuthorizedDevice(device)
    }


    override fun onContextMenuClick(device: Device, id: Int) {
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


}
