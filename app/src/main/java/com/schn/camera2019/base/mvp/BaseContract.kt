package com.schn.camera2019.base.mvp

import androidx.annotation.UiThread


interface BaseContract {

    interface View{
        fun showToast(message: Int)
        fun showToast(message: String)
    }

    interface Presenter<in V : View> {
        var TAG: String

        /** This method will be called when the view has been created.
         *  This sets view to this presenter.*/
        @UiThread
        fun attachView(view: V)

        /** This method will be called when the view has been destroyed.
         *  Typically this method will be invoked from Activity.onDestroy()
         *  or Fragment.onDestroyView()*/
        @UiThread
        fun detachView()
    }

    //BaseLceView,BaseLceFragment,BaseListView,BaseListFragment,BaseLceListView
    interface ListView<M> : View {
        fun showLoading()
        fun showContent()
        fun showError()
        fun showError(msg: Int)
        fun loadData()
        fun setData(data: M)
    }

    interface ListPresenter<M, in V : ListView<M>> : Presenter<V> {
        fun loadData()
    }

}