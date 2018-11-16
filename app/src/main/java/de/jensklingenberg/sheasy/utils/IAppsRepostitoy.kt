package de.jensklingenberg.sheasy.utils

import android.content.pm.ApplicationInfo
import model.AppFile

interface IAppsRepostitoy{
    fun getApps(): List<AppFile>
    fun returnAPK(apkPackageName: String): ApplicationInfo?
}