package de.jensklingenberg.sheasy


import androidx.test.platform.app.InstrumentationRegistry
import de.jensklingenberg.sheasy.data.file.FileRepository
import de.jensklingenberg.sheasy.di.UseCaseModule
import io.mockk.mockk
import io.mockk.spyk

class TestApplication : App() {

// val server: Server= mockk()

lateinit var testAppComponent: TestAppComponent

    var fileRepositoryy : FileRepository = mockk(relaxed=true)

    override fun initializeDagger() {

        testAppComponent = DaggerTestAppComponent.builder()
            .appModule(TestAppModule(this,fileRepositoryy))
            .networkModule(TestNetworkModule())
            .useCaseModule(UseCaseModule())
            .build()

        appComponent=testAppComponent
    }




}

val mockApplication = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
