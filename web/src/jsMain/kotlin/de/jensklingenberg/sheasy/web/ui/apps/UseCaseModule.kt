package de.jensklingenberg.sheasy.web.ui.apps

import de.jensklingenberg.sheasy.web.MyModule
import de.jensklingenberg.sheasy.web.Provides
import de.jensklingenberg.sheasy.web.usecase.NotificationUseCase

class UseCaseModule: MyModule {

    @Provides
    fun prov()= NotificationUseCase()

}