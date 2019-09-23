package com.schn.camera2019.rx

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> rxSchedulerHelperForFlowable(): FlowableTransformer<T, T> = FlowableTransformer { observable ->
    observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> rxSchedulerHelperForObservable(): ObservableTransformer<T, T> = ObservableTransformer { observable ->
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}