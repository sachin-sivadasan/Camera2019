package com.schn.camera2019.permission

import android.Manifest
import androidx.annotation.IntDef

class Action {

    @IntDef(
        ACTION_CODE_READ_CONTACTS,
        ACTION_CODE_READ_IMAGE,
        ACTION_CODE_TAKE_PICTURE,
        ACTION_CODE_CALL_PHONE,
        ACTION_CODE_RECORD_AUDIO,
        ACTION_CODE_ALL
    )
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ActionCode

    companion object {
        const val ACTION_CODE_READ_CONTACTS: Int = 0
        const val ACTION_CODE_READ_IMAGE: Int = 1
        const val ACTION_CODE_TAKE_PICTURE: Int = 2
        const val ACTION_CODE_CALL_PHONE: Int = 3
        const val ACTION_CODE_RECORD_AUDIO: Int = 4
        const val ACTION_CODE_ALL: Int = 5
        const val ACTION_CODE_GPS: Int = 6
        const val ACTION_CODE_FEATURE_CAMERA: Int = 7
        const val ACTION_CODE_FEATURE_VIDEO: Int = 8


        val ACTION_READ_CONTACTS: Action = Action(ACTION_CODE_READ_CONTACTS, Manifest.permission.READ_CONTACTS)
        val ACTION_WRITE_SDCARD: Action = Action(ACTION_CODE_READ_IMAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val ACTION_USE_CAMERA: Action = Action(ACTION_CODE_TAKE_PICTURE, Manifest.permission.CAMERA)
        val ACTION_DIAL: Action = Action(ACTION_CODE_CALL_PHONE, Manifest.permission.CALL_PHONE)
        val ACTION_USE_MIC: Action = Action(ACTION_CODE_RECORD_AUDIO, Manifest.permission.RECORD_AUDIO)
        val ACTION_COARSE_LOCATION: Action = Action(ACTION_CODE_GPS, Manifest.permission.ACCESS_COARSE_LOCATION)
        val ACTION_FINE_LOCATION: Action = Action(ACTION_CODE_GPS, Manifest.permission.ACCESS_FINE_LOCATION)

    }

    @ActionCode
    private val code: Int

    private val permission: String

    constructor(@ActionCode value: Int, name: String) {
        this.code = value
        this.permission = name
    }

    @ActionCode
    fun getCode(): Int {
        return code
    }

    fun getPermission(): String {
        return permission
    }

}