import kotlinext.js.requireAll
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import react.dom.render
import router.hashRouter
import router.route
import router.switch
import ui.home.App
import ui.about.AboutView
import ui.apps.AppsView
import ui.files.FileView
import ui.screenshare.ScreenShareView
import kotlin.browser.document
import kotlin.browser.window

class Application : KodeinAware {
    override val kodein = Kodein {


    }

    init {
        window.onload = {
            kotlinext.js.require("bootstrap/dist/css/bootstrap.min.css")
            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))

            render(document.getElementById("root")) {


                hashRouter {
                    switch {
                        route("/", App::class, exact = true)
                        route("/apps", AppsView::class, exact = true)
                        route("/files",FileView::class,exact = true)
                        route("/about", AboutView::class, exact = true)
                        route("/screenshare", ScreenShareView::class, exact = true)

                    }
                }
            }
        }

    }
}