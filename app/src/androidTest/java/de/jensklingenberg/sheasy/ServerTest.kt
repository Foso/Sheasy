/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.jensklingenberg.sheasy


import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.SheasyApi
import de.jensklingenberg.sheasy.ui.MainActivity
import io.mockk.every
import io.reactivex.Single
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class ServerTest {

    @Inject
    lateinit var server: Server


    @Inject
    lateinit var sheasyApi: SheasyApi

    @Rule
    @JvmField
    var mActivityRule = ActivityTestRule(
        MainActivity::class.java, false, false
    )


    @Before
    fun setup() {
        mockApplication.testAppComponent.inject(this)
        every { mockApplication.testPreferences.httpPort } returns "8766"
        every { mockApplication.testPreferences.webSocketPort } returns 8765

        mActivityRule.launchActivity(null)
        server.start()
        Thread.sleep(500)


    }

    @Test
    fun getSharedTest() {


        val response = sheasyApi.getShared().test()

        Assert.assertEquals(1,response)

    }


    @Test
    fun getAppsTest() {



        every { mockApplication.testPreferences.acceptAllConnections } returns true

        every { mockApplication.androidFileRepository.getApps(any()) } returns Single.just(
            listOf(
                AppInfo(
                    "",
                    "Hallo",
                    "hhh",
                    "dd"
                )
            )
        )

        val response = sheasyApi.getApps().test()
        val body = response.values()[0].body()

        val list = body?.data

        Assert.assertNotNull(list)

        Assert.assertEquals(1, list?.size)

    }


    @After
    fun after() {
       // server.stop()

    }


}