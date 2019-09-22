package com.schn.camera2019.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import com.schn.camera2019.util.LogUtils
import java.util.*

object EasyPermission {

    val TAG: String = "PermissionPresenter--->"
    fun checkAndRequestPermission(host: AppCompatActivity, action: Action) {
        val permissionAction = PermissionActionFactory(host).getPermissionAction()
        if (permissionAction.hasPermission(action.getPermission())) {
            notifyAlreadyHasPermissions(host, action)
        } else {
            if (permissionAction.shouldShowRequestPermissionRationale(action.getPermission())) {
                LogUtils.d(TAG, "called showRationale for " + action.getPermission())
                if (host is PermissionCallback) {
                    host.permissionAccepted(action.getCode())
                }
            } else {
                LogUtils.d(TAG, "called requestPermission for " + action.getPermission())
                permissionAction.requestPermission(action.getPermission(), action.getCode())
            }
        }
    }

    fun canRecordAudio(context: AppCompatActivity): Boolean {
        val permissionAction = PermissionActionFactory(context).getPermissionAction()
        return permissionAction.hasPermission(Action.ACTION_USE_MIC.getPermission())
    }

    fun canRecordVideo(context: AppCompatActivity): Boolean {
        val permissionAction = PermissionActionFactory(context).getPermissionAction()
        return permissionAction.hasPermission(Action.ACTION_USE_MIC.getPermission())
                && permissionAction.hasPermission(Action.ACTION_WRITE_SDCARD.getPermission())
                && permissionAction.hasPermission(Action.ACTION_USE_CAMERA.getPermission())
    }

    fun checkAndRequestPermission(host: AppCompatActivity, vararg actions: Action) {
        val permissionAction = PermissionActionFactory(host).getPermissionAction()
        var needPermissions = false
        val granded_permissions = ArrayList<String>()
        val permissions = ArrayList<String>()
        for (action in actions) {
            if (permissionAction.hasPermission(action.getPermission())) {
                granded_permissions.add(action.getPermission())
            } else {
                needPermissions = true
                permissions.add(action.getPermission())
            }
        }
        if (!needPermissions) {
            if (host is PermissionCallback) {
                host.permissionAccepted(Action.ACTION_CODE_GPS)
            }
        } else {
            permissionAction.requestPermission(permissions.toTypedArray(), Action.ACTION_CODE_GPS)
        }
    }

    fun checkAndRequestCameraPermission(host: AppCompatActivity, vararg actions: Action) {
        val permissionAction = PermissionActionFactory(host).getPermissionAction()
        var needPermissions = false
        val granded_permissions = ArrayList<String>()
        val permissions = ArrayList<String>()
        for (action in actions) {
            if (permissionAction.hasPermission(action.getPermission())) {
                granded_permissions.add(action.getPermission())
            } else {
                needPermissions = true
                permissions.add(action.getPermission())
            }
        }
        if (!needPermissions) {
            if (host is PermissionCallback) {
                host.permissionAccepted(Action.ACTION_CODE_FEATURE_CAMERA)
            }
        } else {
            permissionAction.requestPermission(permissions.toTypedArray(), Action.ACTION_CODE_FEATURE_CAMERA)
        }
    }

    fun checkAndRequestAudioPermission(host: AppCompatActivity, vararg actions: Action) {
        val permissionAction = PermissionActionFactory(host).getPermissionAction()
        var needPermissions = false
        val granded_permissions = ArrayList<String>()
        val permissions = ArrayList<String>()
        for (action in actions) {
            if (permissionAction.hasPermission(action.getPermission())) {
                granded_permissions.add(action.getPermission())
            } else {
                needPermissions = true
                permissions.add(action.getPermission())
            }
        }
        if (!needPermissions) {
            if (host is PermissionCallback) {
                host.permissionAccepted(Action.ACTION_CODE_RECORD_AUDIO)
            }
        } else {
            permissionAction.requestPermission(permissions.toTypedArray(), Action.ACTION_CODE_RECORD_AUDIO)
        }
    }

    fun checkAndRequestVideoPermission(host: AppCompatActivity) {
        val permissionAction = PermissionActionFactory(host).getPermissionAction()
        var needPermissions = false
        val granded_permissions = ArrayList<String>()
        val permissions = ArrayList<String>()
        val action = Action.ACTION_WRITE_SDCARD
        if (permissionAction.hasPermission(action.getPermission())) {
            granded_permissions.add(action.getPermission())
        } else {
            needPermissions = true
            permissions.add(action.getPermission())
        }
        if (!needPermissions) {
            if (host is PermissionCallback) {
                host.permissionAccepted(Action.ACTION_CODE_FEATURE_VIDEO)
            }
        } else {
            permissionAction.requestPermission(permissions.toTypedArray(), Action.ACTION_CODE_FEATURE_VIDEO)
        }
    }

    private fun notifyAlreadyHasPermissions(activity: AppCompatActivity, action: Action) {
        val grantResults = IntArray(1)
        grantResults[0] = PackageManager.PERMISSION_GRANTED
        checkGrantedPermission(grantResults, action.getCode(), activity)
    }

    fun checkGrantedPermission(
        grantResults: IntArray,
        requestCode: Int,
        vararg receivers: Any
    ) {
        // iterate through all receivers
        for (`object` in receivers) {
            if (verifyGrantedPermission(grantResults)) {
                if (`object` is PermissionCallback) {
                    `object`.permissionAccepted(requestCode)
                }
            } else {
                if (`object` is PermissionCallback) {
                    `object`.permissionDenied(requestCode)
                }
            }
        }
    }

    private fun verifyGrantedPermission(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

}