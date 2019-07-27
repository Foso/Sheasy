import de.jensklingenberg.sheasy.web.data.EventDataSource
import de.jensklingenberg.sheasy.web.data.repository.EventRepository
import de.jensklingenberg.sheasy.web.model.Route
import de.jensklingenberg.sheasy.web.ui.about.AboutView
import de.jensklingenberg.sheasy.web.ui.apps.AppsView
import de.jensklingenberg.sheasy.web.ui.common.toolbar
import de.jensklingenberg.sheasy.web.ui.connection.ConnectionView
import de.jensklingenberg.sheasy.web.ui.files.FileView
import de.jensklingenberg.sheasy.web.ui.home.HomeView
import de.jensklingenberg.sheasy.web.ui.screenshare.ScreenShareView
import de.jensklingenberg.sheasy.web.ui.share.ShareView
import kotlinext.js.requireAll
import react.dom.render
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import kotlin.browser.document
import kotlin.browser.window


class Application {

    val routeList = listOf(
        Route("/", HomeView::class, true),
        Route("/apps", AppsView::class, exact = true),
        Route("/files", FileView::class, exact = true),
        Route("/about", AboutView::class, exact = true),
        Route("/screenshare", ScreenShareView::class, exact = true),
        Route("/share", ShareView::class, exact = true),
        Route("/connection", ConnectionView::class, exact = true)
    )

    init {
        window.onload = {
            kotlinext.js.require("bootstrap/dist/css/bootstrap.min.css")
            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))

            render(document.getElementById("root")) {
                toolbar()
                hashRouter {
                    switch {
                        routeList.forEach {
                            route(it.path, it.kClass, it.exact)
                        }

                    }
                }
            }
        }

    }

    companion object{
        val eventDataSource: EventDataSource = EventRepository()

    }
}