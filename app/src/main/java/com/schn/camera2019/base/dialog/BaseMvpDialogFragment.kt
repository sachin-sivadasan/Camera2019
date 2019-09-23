package com.schn.camera2019.base.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.schn.camera2019.R
import com.schn.camera2019.base.mvp.BaseContract
import com.schn.camera2019.util.LogUtils

abstract class BaseMvpDialogFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : BaseView<V, P>(),
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

    protected open fun getDialogStyle(): Int {
        return STYLE_NO_TITLE
    }

    protected open fun getDialogTheme(): Int {
        return STYLE_NORMAL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(getDialogStyle(), getDialogTheme())
    }

    override fun onCreateView(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View {
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        val mRoot: android.view.View = inflater.inflate(getLayoutId(), container, false)!!
        LogUtils.d(TAG, "invoking method initView(view)")
        initView(mRoot)
        return mRoot
    }

    override fun onActivityCreated(savedInstanceState: android.os.Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val window = dialog?.window
        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        window?.attributes?.windowAnimations = R.style.DialogAnimation
        val attributes = window?.attributes

        LogUtils.d(TAG, "invoking method initData()")
        initData()
    }

}