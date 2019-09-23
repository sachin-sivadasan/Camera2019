package com.schn.camera2019.ui.gallery

import com.schn.camera2019.R
import com.schn.camera2019.app.App
import com.schn.camera2019.base.mvp.BasePresenter
import com.schn.camera2019.ui.gallery.list.VideoItemModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.ResourceSingleObserver
import io.reactivex.schedulers.Schedulers
import java.io.File

class GalleryPresenter : BasePresenter<GalleryContract.View>(),
    GalleryContract.Presenter {
    override fun loadData() {
        val file = App.context().getExternalFilesDir(null) ?: return
        val listFiles = arrayListOf<File>()
        listFiles.addAll(file.listFiles())

        val os = Observable.fromIterable(listFiles)
            .filter{it.absolutePath.endsWith(".mp4")}
            .map{ VideoItemModel(it.absolutePath) }
            .toSortedList{ p1, p2 ->
                (p1?.mFile ?: "").compareTo(p2?.mFile ?: "")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : ResourceSingleObserver<List<VideoItemModel>>() {
                override fun onSuccess(data: List<VideoItemModel>) {
                    getMvpView()?.setVideos(ArrayList(data))
                }

                override fun onError(e: Throwable) {
                    getMvpView()?.showError(R.string.error_in_reading_files)
                }

            })
    }
}