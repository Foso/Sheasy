package de.jensklingenberg.sheasy


import androidx.test.platform.app.InstrumentationRegistry
import de.jensklingenberg.sheasy.data.file.AndroidFileRepository
import de.jensklingenberg.sheasy.data.preferences.SheasyPreferencesRepository
import de.jensklingenberg.sheasy.di.UseCaseModule
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.mockk.mockk
import io.mockk.spyk

class TestApplication : App() {

// val server: Server= mockk()

    lateinit var testAppComponent: TestAppComponent

    var androidFileRepository: AndroidFileRepository = mockk(relaxed = true)
    var testPreferences :SheasyPrefDataSource = mockk(relaxed = true)


    override fun initializeDagger() {

        testAppComponent = DaggerTestAppComponent.builder()
            .appModule(TestAppModule(this, androidFileRepository,testPreferences))
            .networkModule(TestNetworkModule())
            .useCaseModule(UseCaseModule())
            .build()

        appComponent = testAppComponent
    }


}

val mockApplication = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
