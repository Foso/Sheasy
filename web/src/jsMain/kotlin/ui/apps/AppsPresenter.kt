package ui.apps

import kotlinext.js.jsObject
import model.AppResponse
import model.Error
import model.Respo
import model.Response
import network.Axios
import network.NetworkUtil

class AppsPresenter(private val view: AppsContract.View) : AppsContract.Presenter {


    var appsResult = listOf<AppResponse>()

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
        Axios.get<Response<Array<AppResponse>>>(NetworkUtil.getApps, jsObject {
            timeout = 10000
        }).then { result ->
            if(result.data.status == "SUCCESS"){
                appsResult = result.data.data!!.toMutableList()
                view.setData(appsResult)

            }else{
                view.showError(Error.NOT_AUTHORIZED)
            }


        }.catch { error ->
            view.showError(Error.NETWORK_ERROR)
            console.log("ERROR"+error)
        }
    }
}
