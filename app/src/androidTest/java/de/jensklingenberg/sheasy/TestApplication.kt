package de.jensklingenberg.sheasy


import androidx.test.platform.app.InstrumentationRegistry
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.data.file.AndroidFileRepository
import de.jensklingenberg.sheasy.di.UseCaseModule
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable

class TestApplication : App() {

// val server: Server= mockk()

    lateinit var testAppComponent: TestAppComponent

    var androidFileRepository: AndroidFileRepository = mockk(relaxed = true)
    var testPreferences :SheasyPrefDataSource = mockk(relaxed = true)
    val devicesRepository:DevicesDataSource = mockk(relaxed = true)


    override fun initializeDagger() {

val list = arrayListOf<Device>()
every { devicesRepository.getAuthorizedDevices() } returns Observable.just(list)

        every { devicesRepository.auth } returns list

        val sharedFolders = arrayListOf(FileResponse("Test","/storage"))

       // every { mockApplication.testPreferences.sharedFolders } returns sharedFolders


        every { mockApplication.testPreferences.acceptAllConnections } returns true

        every { mockApplication.testPreferences.httpPort } returns "8766"
        every { mockApplication.testPreferences.webSocketPort } returns 8765

        testAppComponent = DaggerTestAppComponent.builder()
            .appModule(TestAppModule(this, androidFileRepository,testPreferences))
            .networkModule(TestNetworkModule(devicesRepository))
            .useCaseModule(UseCaseModule())
            .build()

        appComponent = testAppComponent
    }


}

val mockApplication = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
