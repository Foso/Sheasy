package io.ktor.samples.testable

import de.jensklingenberg.sheasy.MockFileRouteHandler
import de.jensklingenberg.sheasy.MockGeneral
import de.jensklingenberg.sheasy.network.ktor.ktorApplicationModule
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import de.jensklingenberg.sheasy.data.preferences.SheasyPreferencesRepository
import repository.SheasyPrefDataSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ApplicationTest {
    val sheasyPreferences: SheasyPrefDataSource =
        SheasyPreferencesRepository()
    val fileRouteHandler:FileRouteHandler=MockFileRouteHandler()
    val generalRouteHandler:GeneralRouteHandler=MockGeneral()


    @Test
    fun testRequests() = withTestApplication(moduleFunction = {ktorApplicationModule(sheasyPreferences,generalRouteHandler,fileRouteHandler)}) {
        with(handleRequest(HttpMethod.Get, "/")) {

            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("Hello from Ktor Testable sample application", response.content)
        }
        with(handleRequest(HttpMethod.Get, "/index.html")) {
            assertFalse(requestHandled)
        }
    }
}
