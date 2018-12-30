package de.jensklingenberg.react.ui.apps

import de.jensklingenberg.react.data.AppsDataSource
import de.jensklingenberg.model.App
import de.jensklingenberg.model.Error

class AppsPresenter(private val view: AppsContract.View,val appsDataSource : AppsDataSource) : AppsContract.Presenter {


    var appsResult = listOf<App>()

    /****************************************** React Lifecycle methods  */
    override fun componentWillUnmount() {}

    override fun componentDidMount() {}

    /****************************************** Presenter methods  */
    override fun onSearch(query: String) {
        appsResult
            .filter {
                it.name.contains(query, true)
            }
            .run(view::setData)
    }

    override fun getApps() {
     appsDataSource.getApps(onSuccess = {
            view.setData(it)

        },onError = {
            view.showError(Error.NETWORK_ERROR)
        })


    }
}
