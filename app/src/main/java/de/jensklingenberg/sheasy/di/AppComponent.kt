package de.jensklingenberg.sheasy.di

import android.arch.lifecycle.ViewModel
import dagger.Component
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.handler.RequestHandlerFactory
import de.jensklingenberg.sheasy.network.service.HTTPServerService
import de.jensklingenberg.sheasy.network.websocket.MyHttpServerImpl
import de.jensklingenberg.sheasy.network.websocket.MyWebSocket
import de.jensklingenberg.sheasy.network.websocket.NotificationWebsocket
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (RemoteModule::class), (BroadcastReceiverModule::class)])
@Singleton
interface AppComponent {

    fun inject(currencyViewModel: ViewModelFactory)
    fun inject(viewModel: ViewModel) {

    }

    fun inject(baseFragment: BaseFragment) {

    }

    fun inject(httpServerService: HTTPServerService) {

    }

    fun inject(myHttpServerImpl: MyHttpServerImpl) {

    }

    fun inject(requestHandlerFactory: RequestHandlerFactory) {

    }

    fun inject(notificationWebsocket: NotificationWebsocket) {

    }

    fun inject(screenSharWebsocket: MyWebSocket) {

    }


}
