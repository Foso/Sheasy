package de.jensklingenberg.sheasy.di

import android.content.pm.ApplicationInfo
import de.jensklingenberg.sheasy.utils.IAppsRepostitoy
import model.AppFile

class MockAppsRespository : IAppsRepostitoy {
    override fun getApps(): List<AppFile> {
        return listOf(AppFile("TestApp1","com.test.de","sakdlfjaslfjk"))

    }

    override fun returnAPK(apkPackageName: String): ApplicationInfo? {
       return ApplicationInfo()
    }
}