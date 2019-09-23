package com.schn.camera2019.base.fragment

import android.view.View
import com.schn.camera2019.R
import com.schn.camera2019.base.mvp.BaseContract
import com.schn.camera2019.base.mvp.BaseView
import com.schn.camera2019.util.LogUtils

abstract class BaseMvpFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : BaseView<V, P>(),
    FragmentInterface,
    View.OnClickListener {

    open var TAG = "BaseMvpViewFragment-->"

    /**
     * All the view initializations should be done inside this method.
     * */
    override fun initView(view: View) {}

    /**
     * All the logic for data that needed in view should be done inside
     * this method.
     * */
    override fun initData() {}

    /**
     * This method will called by when user clicks on views.
     * */
    override fun onClick(v: View?) {}

    /**
     * This method will called by activity that holds this fragment,
     * when user press back button.
     * */
    override fun onBackPressed(): Boolean {
        return false
    }

    /**
     * Override this method to put the correct layout that
     * needs for the screen.
     * */
    protected open fun getLayoutId(): Int {
        return R.layout.fragment_base
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View {
        val mRoot: View = inflater.inflate(getLayoutId(), container, false)!!
        initView(mRoot)
        return mRoot
    }

    override fun onActivityCreated(savedInstanceState: android.os.Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.d(TAG, "invoking method initData()")
        initData()
    }

}