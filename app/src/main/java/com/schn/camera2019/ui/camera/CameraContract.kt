package com.schn.camera2019.ui.camera

import com.schn.camera2019.base.mvp.BaseContract

interface CameraContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter<View>
}