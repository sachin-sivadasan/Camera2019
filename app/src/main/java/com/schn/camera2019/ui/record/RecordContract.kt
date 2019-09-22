package com.schn.camera2019.ui.record

import com.schn.camera2019.base.mvp.BaseContract

interface RecordContract {

    interface View : BaseContract.ListView<VideoFile>

    interface Presenter : BaseContract.ListPresenter<VideoFile, View>
}