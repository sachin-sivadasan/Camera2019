package com.schn.camera2019.base.mvp

interface MvpDelegates<V : BaseContract.View, P : BaseContract.Presenter<V>> {
    /*This method creates the presenter instance.*/
    fun createPresenter(): P

    /*This method set the presenter instance.*/
    fun setPresenter(presenter: P)

    /*This method returns the presenter instance.*/
    fun getMvpPresenter(): P?

    /*This method return the view instance.*/
    fun getMvpView(): V

    /*This method returns the parent view instance.*/
    fun findMvpView(): V
}