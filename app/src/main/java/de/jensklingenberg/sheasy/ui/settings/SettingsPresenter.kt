package de.jensklingenberg.sheasy.ui.settings

import android.app.Application
import android.content.Context
import android.content.Intent
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.usecase.CheckPermissionUseCase
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.*
import de.jensklingenberg.sheasy.utils.NetworkUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingsPresenter(val view: SettingsContract.View) : SettingsContract.Presenter {


    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var context: Context

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var checkPermissionUseCase: CheckPermissionUseCase

    @Inject
    lateinit var application: Application

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onCreate() {

        val list = listOf<BaseDataSourceItem<*>>(
            GenericListHeaderSourceItem(
                "Server"
            ),
            GenericListItem(
                "IP Address",
                NetworkUtils.getIP(context),
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem(),

            GenericListItem(
                context.getString(R.string.http_port),
                sheasyPrefDataSource.httpPort,
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem()

            ,

            GenericListHeaderSourceItem(
                "Connections"
            ), GenericToggleItemSourceItem(
                GenericToggleItem(
                    context.getString(R.string.acceptAllConnections),
                    "If active, all incoming requests are accepted automatic",
                    R.drawable.ic_info_outline_grey_700_24dp,
                    sheasyPrefDataSource.acceptAllConnections
                ) { value -> sheasyPrefDataSource.acceptAllConnections = value }),

            GenericListHeaderSourceItem(
                "Folders"
            ),
            GenericListItem(
                context.getString(R.string.appFolder),
                sheasyPrefDataSource.appFolder,
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem(),
            GenericListHeaderSourceItem(
                "Permissions"
            ),
            GenericToggleItem(
                context.getString(R.string.readNotifications),
                "If active, you can receive device notifications in the browser interface",
                R.drawable.ic_info_outline_grey_700_24dp,
                checkPermissionUseCase.checkNotificationPermission()
            ) { value ->
                if (value) {
                    checkPermissionUseCase.requestNotificationPermission()
                }
            }.toSourceItem()

        )

        view.setData(list)

    }

    override fun startService(intent: Intent) {
        application.startService(intent)
    }

    override fun stopService(intent: Intent) {
        application.stopService(intent)
    }


    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}