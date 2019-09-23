package com.schn.camera2019.ui.gallery

import com.schn.camera2019.base.mvp.BaseContract
import com.schn.camera2019.ui.gallery.list.VideoItemModel

interface GalleryContract {

    interface View : BaseContract.ListView<VideoItemModel> {
        fun setVideos(data: ArrayList<VideoItemModel>)
    }

    interface Presenter : BaseContract.ListPresenter<VideoItemModel, View>
}