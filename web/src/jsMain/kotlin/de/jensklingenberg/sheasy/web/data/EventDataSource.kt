package de.jensklingenberg.sheasy.web.data

import de.jensklingenberg.sheasy.model.ClientEvent
import de.jensklingenberg.sheasy.web.model.NotificationOptions
import de.jensklingenberg.sheasy.web.model.SourceItem
import kodando.rxjs.Observable

interface EventDataSource {

    fun sendMessage(message: String)
    fun observeEvents(): Observable<List<SourceItem>>
    fun observNotifcations(): Observable<NotificationOptions>


    fun observeClientEvents(): Observable<ClientEvent>
}