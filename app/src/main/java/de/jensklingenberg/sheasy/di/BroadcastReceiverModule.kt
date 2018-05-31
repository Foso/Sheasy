package de.jensklingenberg.sheasy.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import javax.inject.Singleton

@Module
class BroadcastReceiverModule {


    @Provides
    @Singleton
    fun provideMySharedMessageBroadcastReceiver(): MySharedMessageBroadcastReceiver {
        return MySharedMessageBroadcastReceiver()

    }


}