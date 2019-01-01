package network.routes

import io.ktor.application.call
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import main.MockTestDataSource
import de.jensklingenberg.sheasy.web.model.Resource

fun Route.apps() {
    get("/apps") {
        call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
        call.respond(Resource.success(MockTestDataSource.mockAppList))
    }

}