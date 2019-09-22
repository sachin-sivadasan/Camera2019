package com.schn.camera2019.base.fragment

import android.view.View

interface FragmentInterface {
    fun initView(view: View)
    fun initData()
    fun onBackPressed(): Boolean
}