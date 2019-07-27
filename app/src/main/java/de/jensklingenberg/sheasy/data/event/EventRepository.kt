package de.jensklingenberg.sheasy.data.event

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.ClientEvent
import de.jensklingenberg.sheasy.model.Event
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class EventRepository : EventDataSource {


    override val clientEventSubject: PublishSubject<ClientEvent> = PublishSubject.create()

    private val eventSubject: PublishSubject<List<Event>> = PublishSubject.create()
    val list = mutableListOf<Event>()

    init {
        initializeDagger()
    }


    private fun initializeDagger() = App.appComponent.inject(this)


    override fun getEvents(): Observable<List<Event>> {
        return eventSubject.hide()
    }

    override fun addEvent(event: Event) {
        list.add(event)
        eventSubject.onNext(list)
    }

    override fun addClientEvent(event: ClientEvent) {
        clientEventSubject.onNext(event)

    }

    override fun observeClientEvents(): Observable<ClientEvent> {
        return clientEventSubject

    }
}