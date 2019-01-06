package de.jensklingenberg.sheasy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.utils.PermissionUtils
import de.jensklingenberg.sheasy.utils.UseCase.MessageUseCase
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import de.jensklingenberg.sheasy.utils.UseCase.VibrationUseCase
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {


    @Provides
    @Singleton
    fun provideVibrationUseCase(context: Context): VibrationUseCase = VibrationUseCase(context)

    @Provides
    @Singleton
    fun providePermissionUtils(): PermissionUtils = PermissionUtils()

    @Provides
    @Singleton
    fun provideShareUseCase():ShareUseCase = ShareUseCase()

    @Provides
    @Singleton
    fun provideMessageUseCase():MessageUseCase=MessageUseCase()


    @Provides
    @Singleton
    fun provideNotifUtils(): NotificationUseCase = NotificationUtils()
}