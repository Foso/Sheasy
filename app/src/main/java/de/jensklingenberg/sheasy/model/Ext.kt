package de.jensklingenberg.sheasy.model

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

fun ApplicationInfo.mapToAppInfo(pm: PackageManager): AppInfo {
    return AppInfo(
        sourceDir = this.sourceDir,
        name = pm.getApplicationLabel(this).toString(),
        packageName = this.packageName,
        installTime = pm.getPackageInfo(
            this.packageName,
            0
        ).firstInstallTime.toString()
    )
}