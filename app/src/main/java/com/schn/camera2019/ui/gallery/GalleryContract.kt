package com.schn.camera2019.ui.gallery

import com.schn.camera2019.base.mvp.BaseContract

interface GalleryContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter<View>
}