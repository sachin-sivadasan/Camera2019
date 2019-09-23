package com.schn.camera2019.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

// PublishSubject will only emit data from the original Flowable
// to the observer after the point in time when the subscription occurred.
object RxBus {
    /**
     *  A Subject that emits events to currently subscribed Observers
     * */
    var bus: PublishSubject<Any> = PublishSubject.create()

    // provided a new event to launch data
    fun post(o: Any) {
        bus.onNext(o)
    }

    fun <T> toObserverable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }

}