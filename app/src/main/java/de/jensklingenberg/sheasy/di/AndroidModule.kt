package de.jensklingenberg.sheasy.di

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.media.projection.MediaProjectionManager
import android.net.wifi.WifiManager
import android.os.Vibrator
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.utils.extension.mediaProjectionManager
import de.jensklingenberg.sheasy.utils.extension.notificationManager
import de.jensklingenberg.sheasy.utils.extension.wifiManager
import de.jensklingenberg.sheasy.utils.extension.windowManager
import javax.inject.Singleton


/**
 * This module contains Android specific "managers" from SystemService
 */

@Module
class AndroidModule {


    @Provides
    @Singleton
    fun provideNotificationManager(context: Context): NotificationManager =
        context.notificationManager()

    @Provides
    @Singleton
    fun providePackageManager(context: Context): PackageManager = context.packageManager

    @Provides
    @Singleton
    fun provideWindowManager(context: Context): WindowManager = context.windowManager()

    @Provides
    @Singleton
    fun provideWifiManager(context: Context): WifiManager = context.wifiManager()

    @Provides
    @Singleton
    fun provideAssetManger(context: Context): AssetManager = context.assets

    @Provides
    @Singleton
    fun provideMediaProjectionManager(context: Context): MediaProjectionManager =
        context.mediaProjectionManager()

    @Provides
    @Singleton
    fun provideVibrator(context: Context): Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator


}