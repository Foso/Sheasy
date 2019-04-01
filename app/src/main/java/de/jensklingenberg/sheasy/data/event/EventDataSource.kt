package de.jensklingenberg.sheasy.data.event

import de.jensklingenberg.sheasy.model.Event
import io.reactivex.Observable

interface EventDataSource {

    fun getEvents(): Observable<List<Event>>
    fun addEvent(event: Event)

}
