package de.jensklingenberg.sheasy.utils.extension

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import de.jensklingenberg.sheasy.model.Resource


class NonNullMediatorLiveData<T> : MediatorLiveData<T>()
class ResourceMediatorLiveData<T> : MediatorLiveData<T>()


fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this, Observer { it?.let { mediator.value = it } })
    return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, android.arch.lifecycle.Observer {
        it?.let(observer)
    })
}

fun <T> ResourceMediatorLiveData<Resource<T>>.observe2(
    owner: LifecycleOwner,
    observer: (t: T) -> Unit
): ResourceMediatorLiveData<T> {

    val mediator: ResourceMediatorLiveData<T> = ResourceMediatorLiveData()
    mediator.addSource(this, Observer { it?.let { mediator.value = it.data } })
    return mediator


}

fun <T> NonNullMediatorLiveData<T>.onSuccess(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, android.arch.lifecycle.Observer {
        it?.let(observer)
    })
}