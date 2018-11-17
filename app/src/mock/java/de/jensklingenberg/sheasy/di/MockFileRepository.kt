package de.jensklingenberg.sheasy.di

import android.content.pm.ApplicationInfo
import de.jensklingenberg.sheasy.data.FileRepository
import model.AppFile

class MockFileRepository : FileRepository() {


    override fun getApps(): List<AppFile> {
        return listOf(AppFile("TestApp1","com.test.de","sakdlfjaslfjk"),
            AppFile("TestApp2","com.test.de","sakdlfjaslfjk"))

    }

    override fun getApplicationInfo(apkPackageName: String): ApplicationInfo? {
       return ApplicationInfo()
    }
}