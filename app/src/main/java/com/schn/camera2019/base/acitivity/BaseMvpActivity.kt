package com.schn.camera2019.base.acitivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.schn.camera2019.base.mvp.BaseContract
import com.schn.camera2019.base.mvp.MvpDelegates
import com.schn.camera2019.util.LogUtils

abstract class BaseMvpActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> : AppCompatActivity(),
    BaseContract.View,
    MvpDelegates<V, P> {

    private var mPresenter: P? = null

    abstract override fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val presenter = createPresenter()
        LogUtils.d("camera2019-->", "presenter created $presenter")
        setPresenter(presenter)
        LogUtils.d("camera2019-->", "presenter attaching view " + getMvpView())
        getMvpPresenter().attachView(getMvpView())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        postCreate(savedInstanceState)
    }

    protected open fun postCreate(bundle: Bundle?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        val mvpPresenter = getMvpPresenter()
        LogUtils.d("camera2019-->", "presenter detaching view " + getMvpView())
        mvpPresenter.detachView()
    }

    override fun getMvpPresenter(): P {
        return mPresenter!!
    }

    override fun setPresenter(presenter: P) {
        mPresenter = presenter
    }

    override fun getMvpView(): V {
        return this as V
    }

    /**
     * show toast messages here
     **/
    override fun showToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * show toast messages here
     **/
    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * set visibility of views to gone here.
     **/
    fun gone(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.GONE
            }
        }
    }

    /**
     * set visibility of views to visible here.
     **/
    fun visible(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.VISIBLE
            }
        }
    }
}