package com.schn.camera2019.base.mvp

open class BasePresenter<V : BaseContract.View> :
    BaseContract.Presenter<V> {

    override var TAG: String = ""

    private var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    fun getMvpView(): V? {
        return mView
    }

}