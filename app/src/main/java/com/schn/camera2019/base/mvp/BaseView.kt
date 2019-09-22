package com.schn.camera2019.base.mvp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseView<V : BaseContract.View, P : BaseContract.Presenter<V>> :
        Fragment(),
    BaseContract.View,
    MvpDelegates<V, P> {

    private lateinit var mPresenter: P
    abstract override fun createPresenter(): P
    abstract override fun findMvpView(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val presenter = createPresenter()
        setPresenter(presenter)
    }

    override fun setPresenter(presenter: P) {
        mPresenter = presenter
    }

    override fun getMvpPresenter(): P = mPresenter
    override fun getMvpView(): V = findMvpView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView(getMvpView())
        viewCreated(view, savedInstanceState)
    }

    open fun viewCreated(view: View, bundle: Bundle?): Boolean {
        return true
    }

    override fun onDestroyView() {
        dropView()
        super.onDestroyView()
    }

    fun setView(view: V) {
        getMvpPresenter().attachView(view)
    }

    fun dropView() {
        getMvpPresenter().detachView()
    }
    override fun showToast(message: Int) {}

    override fun showToast(message: String) {}

}