package com.schn.camera2019.base.mvp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.schn.camera2019.R

abstract class BaseMvpLceView<CV : View, M, V : BaseContract.ListView<M>, P : BaseContract.Presenter<V>> :
        BaseMvpView<V, P>(),
        BaseContract.ListView<M> {

    // holds the loading view
    lateinit var mLoadingView: View

    // holds the main view
    lateinit var mContentView: CV

    // holds the error view
    lateinit var mErrorView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingView = view.findViewById(R.id.loadingView)
        mContentView = view.findViewById(R.id.contentView)
        mErrorView = view.findViewById(R.id.errorView)
    }
    // show loading view
    override fun showLoading() {
        visible(mLoadingView)
    }

    // hide the loading view and show content view.
    override fun showContent() {
        gone(mLoadingView, mErrorView)
        visible(mContentView)
    }

    // hide the loading view and show error message.
    override fun showError() {
        gone(mLoadingView)
        visible(mErrorView)
    }

    // hide the loading view and show error message.
    override fun showError(msg: Int) {
        gone(mLoadingView)
        visible(mErrorView)
    }
}