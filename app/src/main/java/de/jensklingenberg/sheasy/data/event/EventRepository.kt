package de.jensklingenberg.sheasy.data.event

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.Event
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class EventRepository : EventDataSource {


    init {
        initializeDagger()
    }


    private fun initializeDagger() = App.appComponent.inject(this)


    val list = mutableListOf<Event>()

    val appsSubject: PublishSubject<List<Event>> = PublishSubject.create<List<Event>>()


    override fun getEvents(): Observable<List<Event>> {
        return appsSubject.hide()
    }

    override fun addEvent(event: Event) {
        list.add(event)
        appsSubject.onNext(list)
    }


}