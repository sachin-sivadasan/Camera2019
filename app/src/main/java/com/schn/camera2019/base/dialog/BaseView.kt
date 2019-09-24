package com.schn.camera2019.base.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.schn.camera2019.base.mvp.BaseContract
import com.schn.camera2019.base.mvp.MvpDelegates

abstract class BaseView<V : BaseContract.View, P : BaseContract.Presenter<V>> :
        DialogFragment(),
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

    override fun showToast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}