package de.jensklingenberg.sheasy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.utils.PermissionUtils
import de.jensklingenberg.sheasy.utils.UseCase.MessageUseCase
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import de.jensklingenberg.sheasy.utils.UseCase.VibrationUseCase
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
}