package de.jensklingenberg.sheasy.web.ui.apps

import de.jensklingenberg.sheasy.web.data.AppsDataSource
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.Error

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
