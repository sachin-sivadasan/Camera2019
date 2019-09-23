package com.schn.camera2019.base.dialog

import android.view.View

interface FragmentInterface {
    fun initView(view: View)
    fun initData()
    fun onBackPressed(): Boolean
}