package ui.apps

import kotlinext.js.jsObject
import model.AppResponse
import network.Axios
import network.NetworkUtil

class AppsPresenter(private val view: AppsContract.View) : AppsContract.Presenter {

    var appsResult = listOf<AppResponse>()
    val mockList = listOf(
        AppResponse("TestData", "11111", "test.package"),
        AppResponse("ABC", "11111", "test.package")
    )


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
        Axios.get<Array<AppResponse>>(NetworkUtil.getApps, jsObject {
            timeout = 10000
        }).then { result ->
            appsResult = result.data.sortedBy { it.name }
            view.setData(appsResult)
        }.catch { error ->
            appsResult = mockList
            view.setData(appsResult)
            console.log(error)
        }
    }
}