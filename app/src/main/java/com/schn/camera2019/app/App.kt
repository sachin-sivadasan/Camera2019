package com.schn.camera2019.app

import android.app.Application
import android.content.Context

/**
 * Created by acer on 20-11-2018.
 */
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