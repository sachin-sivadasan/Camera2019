package com.schn.camera2019.ui.gallery

import com.schn.camera2019.base.fragment.BaseMvpFragment

class GalleryView : BaseMvpFragment<GalleryContract.View, GalleryContract.Presenter>(),
    GalleryContract.View {

    override fun createPresenter(): GalleryContract.Presenter {
        return GalleryPresenter()
    }

    override fun findMvpView(): GalleryContract.View {
        return this
    }
}