package de.jensklingenberg.sheasy.data.event

import de.jensklingenberg.sheasy.model.Event
import io.reactivex.Observable


class EventRepository:EventDataSource{

    val list = mutableListOf<Event>()

    override fun getEvents(): Observable<List<Event>> {
        return Observable.just(list)
    }

    override fun addEvent(event: Event) {
        list.add(event)
    }

}