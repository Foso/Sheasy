package de.jensklingenberg.sheasy.di

import android.content.Context
import android.os.Vibrator
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.usecase.CheckPermissionUseCase
import de.jensklingenberg.sheasy.data.notification.NotificationUtils
import de.jensklingenberg.sheasy.utils.PermissionUtils
import de.jensklingenberg.sheasy.data.usecase.MessageUseCase
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import de.jensklingenberg.sheasy.data.usecase.VibrationUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {


    @Provides
    @Singleton
    fun provideVibrationUseCase(context: Vibrator): VibrationUseCase =
        VibrationUseCase(context)

    @Provides
    @Singleton
    fun providePermissionUtils(): PermissionUtils = PermissionUtils()

    @Provides
    @Singleton
    fun provideShareUseCase(): ShareUseCase =
        ShareUseCase()

    @Provides
    @Singleton
    fun provideMessageUseCase(): MessageUseCase =
        MessageUseCase()


    @Provides
    @Singleton
    fun provideNotifUtils(): NotificationUseCase = NotificationUtils()

    @Provides
    @Singleton
    fun provideCheckPermissionUseCase(context: Context): CheckPermissionUseCase =
        CheckPermissionUseCase(context)
}