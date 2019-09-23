package com.schn.camera2019.ui.main

import com.schn.camera2019.base.mvp.BaseContract

interface MainContract {

    interface View : BaseContract.View {
    }

    interface Presenter : BaseContract.Presenter<View> {
    }
}