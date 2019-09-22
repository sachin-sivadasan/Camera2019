package com.schn.camera2019.permission

interface PermissionCallback {
    fun permissionAccepted(actionCode: Int)
    fun permissionDenied(actionCode: Int)
    fun showRationale(actionCode: Int)
}