package com.schn.camera2019.ui.splash

import com.schn.camera2019.base.mvp.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashPresenter : BasePresenter<SplashContract.View>(), SplashContract.Presenter {
    override fun startCountDown() {
        val subscribe = Observable.timer(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { aLong ->
                getMvpView()?.finishSplash()
            }
    }
}