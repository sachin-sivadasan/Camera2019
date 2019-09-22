package com.schn.camera2019.base.mvp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseMvpView<V : BaseContract.View, P : BaseContract.Presenter<V>> :
    Fragment(),
    BaseContract.View,
    MvpDelegates<V, P> {

    private var mPresenter: P? = null

    abstract override fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val presenter = createPresenter()
        setPresenter(presenter)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMvpPresenter().attachView(getMvpView())
        _onViewCreated(view, savedInstanceState)
    }

    open fun _onViewCreated(view: View, bundle: Bundle?) {}

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpPresenter().detachView()
    }

    override fun getMvpPresenter(): P {
        return mPresenter!!
    }

    override fun setPresenter(presenter: P) {
        mPresenter = presenter
    }

    override fun getMvpView(): V {
        return findMvpView()
    }

    fun gone(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.GONE
            }
        }
    }

    fun visible(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.VISIBLE
            }
        }
    }

    fun inVisible(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.INVISIBLE
            }
        }
    }

    fun enable(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.isEnabled = true
            }
        }
    }

    fun disable(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.isEnabled = false
            }
        }
    }

    override fun showToast(message: Int) {}

    override fun showToast(message: String) {}

}