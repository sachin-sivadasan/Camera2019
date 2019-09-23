package com.schn.camera2019.ui.splash

import com.schn.camera2019.base.mvp.BaseContract

interface SplashContract {

    interface View : BaseContract.View {
        fun finishSplash()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun startCountDown()
    }
}