package de.jensklingenberg.sheasy.ui.pairedDevices

import android.content.Context
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AuthorizationType
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.ui.common.addTo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class PairedPresenter(val view: PairedContract.View) : PairedContract.Presenter {
    override fun onPopupMenuClicked(device: Device, id: Int) {
        when (id) {
            R.id.menu_revoke -> {
                revokeDevice(device)
                true
            }
            else -> {
                true
            }
        }

    }

    @Inject
    lateinit var context: Context

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)



    override fun onCreate() {
        loadPairedDevices()

    }

    private fun loadPairedDevices() {

        getAuthro()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                view.setData(it)
            }.addTo(compositeDisposable)


    }


    override fun revokeDevice(device: Device) {
        sheasyPrefDataSource.devicesRepository.removeDevice(device)
        // loadPairedDevices()

    }

    override fun onDestroy() {
        compositeDisposable.dispose()

    }

    fun getAuthro(): Observable<ArrayList<BaseDataSourceItem<*>>> {
        return sheasyPrefDataSource.devicesRepository.getAuthorizedDevices()
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
    }




}
