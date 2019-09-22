package com.schn.camera2019.permission

import android.app.Activity

class PermissionActionFactory {

    private var mActivity: Activity

    constructor(mActivity: Activity) {
        this.mActivity = mActivity
    }

    fun getPermissionAction(): PermissionAction {
        return PermissionActionImpl(mActivity)
    }
}