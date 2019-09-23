package com.schn.camera2019.app

import android.app.Application
import android.content.Context
import com.schn.camera2019.util.LogUtils

class App : Application() {
    companion object {
        lateinit var instance: App

        @JvmStatic
        fun context(): Context = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtils.init()
    }

}