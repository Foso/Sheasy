import kotlinext.js.requireAll
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import react.dom.render
import components.router.hashRouter
import components.router.route
import components.router.switch
import de.jensklingenberg.react.ui.home.App
import de.jensklingenberg.react.ui.about.AboutView
import de.jensklingenberg.react.ui.apps.AppsView
import de.jensklingenberg.react.ui.files.FileView
import de.jensklingenberg.react.ui.screenshare.ScreenShareView
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
                        route("/files", FileView::class, exact = true)
                        route("/about", AboutView::class, exact = true)
                        route("/screenshare", ScreenShareView::class, exact = true)

                    }
                }
            }
        }

    }
}