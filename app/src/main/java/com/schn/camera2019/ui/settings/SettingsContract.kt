package com.schn.camera2019.ui.settings

import com.schn.camera2019.base.mvp.BaseContract

interface SettingsContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter<View>
}