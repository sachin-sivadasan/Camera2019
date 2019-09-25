package com.schn.camera2019.ui.player

import com.schn.camera2019.base.mvp.BaseContract

interface VideoPlayContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter<View>
}