package com.schn.camera2019.permission

interface PermissionAction {
    fun requestPermission(permission: String, requestCode: Int)
    fun requestPermission(permissions: Array<String>, requestCode: Int)
    fun hasPermission(permission: String): Boolean
    fun shouldShowRequestPermissionRationale(permission: String): Boolean
}