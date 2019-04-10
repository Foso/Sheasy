package de.jensklingenberg.sheasy.ui.settings

import android.content.Context
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.CheckPermissionUseCase
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListItem
import de.jensklingenberg.sheasy.ui.common.GenericToggleItem
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import de.jensklingenberg.sheasy.utils.NetworkUtils
import javax.inject.Inject

class SettingsPresenter(val view: SettingsContract.View) : SettingsContract.Presenter {


    @Inject
    lateinit var context: Context

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var checkPermissionUseCase: CheckPermissionUseCase


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
                sheasyPrefDataSource.httpPort.toString(),
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem()

            ,
            GenericListItem(
                context.getString(R.string.websocket_port),
                sheasyPrefDataSource.webSocketPort.toString(),
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem(),

            GenericListHeaderSourceItem(
                "Settings"
            ),
            GenericToggleItem(
                context.getString(R.string.acceptAllConnections),
                sheasyPrefDataSource.webSocketPort.toString(),
                R.drawable.ic_info_outline_grey_700_24dp,
                sheasyPrefDataSource.acceptAllConnections
            ) { value -> sheasyPrefDataSource.acceptAllConnections = value }.toSourceItem(),

            GenericListHeaderSourceItem(
                "Permissions"
            ),
            GenericToggleItem(
                context.getString(R.string.readNotifications),
                sheasyPrefDataSource.webSocketPort.toString(),
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


    override fun onItemClicked(payload: Any) {

    }

}