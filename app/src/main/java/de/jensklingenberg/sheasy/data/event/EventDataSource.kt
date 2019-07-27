package de.jensklingenberg.sheasy.data.event

import de.jensklingenberg.sheasy.model.ClientEvent
import de.jensklingenberg.sheasy.model.Event
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface EventDataSource {
    val clientEventSubject: PublishSubject<ClientEvent>
    fun getEvents(): Observable<List<Event>>
    fun addEvent(event: Event)

    fun addClientEvent(event: ClientEvent)


    fun observeClientEvents(): Observable<ClientEvent>
}
