package ui.apps

import kotlinext.js.jsObject
import model.AppFile
import network.Axios
import network.AxiosRequestConfig
import network.NetworkUtil

class AppsPresenter(val view: AppsContract.View) : AppsContract.Presenter {
    override fun componentWillUnmount() {


    }

    var appsResult = mutableListOf<AppFile>()


    override fun onSearch(query: String) {
        console.log(query)
        appsResult
                .filter { it.name.contains(query, true) }
                .run(view::setData)

    }


    override fun getApps() {
        val tt: AxiosRequestConfig = jsObject {
            timeout = 10000
        }

        Axios.get<Array<AppFile>>(NetworkUtil.getApps, tt).then { result ->
            appsResult = result.data.toMutableList()
            appsResult.sortBy { it.name }
            view.setData(appsResult)

        }.catch { error ->

            val testApps = AppFile("TestData", "11111", "test.package")
            val testApps2 = AppFile("ABC", "11111", "test.package")

            appsResult = arrayOf(testApps, testApps2).toMutableList()


            view.setData(appsResult)

            console.log(error)

        }

    }


    override fun componentDidMount() {

    }


}