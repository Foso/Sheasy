package react

import de.jensklingenberg.react.data.AppsDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import de.jensklingenberg.model.App
import de.jensklingenberg.model.response.Response
import org.junit.Before
import org.junit.Test
import de.jensklingenberg.react.ui.apps.AppsContract
import de.jensklingenberg.react.ui.apps.AppsPresenter


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppsPresenterTest  {

    val appsDataSource = mockk<AppsDataSource>()
    val appsView = mockk<AppsContract.View>(relaxed = true)

    @Before
    fun setup() {

        val response = Response.success(listOf(App("dd","dd","hh")))
        every { appsDataSource.getApps(onSuccess = {},onError = {}) }

    }


    @Test
    fun getApps() {

        val appsPresenter = AppsPresenter(appsView, appsDataSource)
        appsPresenter.getApps()
        verify { appsView.setData(any()) }
    }


}
