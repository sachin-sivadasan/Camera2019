package com.schn.camera2019.ui.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.schn.camera2019.R
import com.schn.camera2019.app.App
import com.schn.camera2019.base.recycler.adapter.BaseRecyclerViewHolder
import com.schn.camera2019.base.recycler.adapter.RecyclerViewAdapter
import com.schn.camera2019.base.recycler.adapter.RecyclerViewHolder
import com.schn.camera2019.base.recycler.view.BaseRecyclerFragment
import com.schn.camera2019.permission.EasyPermission
import com.schn.camera2019.rx.Event
import com.schn.camera2019.rx.RxBus
import com.schn.camera2019.ui.gallery.list.VideoItemModel
import com.schn.camera2019.ui.gallery.list.VideoItemView
import com.schn.camera2019.ui.main.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import com.schn.camera2019.ui.gallery.list.SingleClick
import com.schn.camera2019.ui.player.VideoPlayView

class GalleryView : BaseRecyclerFragment<FrameLayout, VideoItemModel, GalleryContract.View, GalleryContract.Presenter>(),
    GalleryContract.View {

    private lateinit var videoPath: String
    override var mListAdapter: RecyclerViewAdapter<VideoItemModel> = MyAdapter()

    override fun initListView() {

    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_record
    }

    override fun loadData() {
        getMvpPresenter().loadData()
    }

    override fun setData(file: VideoItemModel) {}

    override fun createPresenter(): GalleryContract.Presenter = GalleryPresenter()

    override fun findMvpView(): GalleryContract.View = this
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (EasyPermission.canRecordVideo(activity as AppCompatActivity)) {
//            openCamera()
        }
    }

    private fun openCamera() {
        val absolutePath = Environment.getExternalStorageDirectory().absolutePath
        val videoName = "video_" + System.currentTimeMillis() + ".mp4"
        videoPath = absolutePath + File.pathSeparator + videoName
        val mediaFile = File(videoPath)
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val authority = App.context().applicationContext.packageName + ".provider"
        val videoUri = FileProvider.getUriForFile(App.context(), authority, mediaFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 180)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) run {
            if (resultCode == Activity.RESULT_OK) {
//                val absolutePath = Environment.getExternalStorageDirectory().absolutePath
//                val videoName = "video_" + System.currentTimeMillis() + ".mp4"
//                val videoPath = absolutePath + File.pathSeparator + videoName
                handleVideoIntent(videoPath)
            }
        }
    }

    private fun handleVideoIntent(path: String) {
        val file = File(path)
        val length = file.length()
        val fileSize = getFileSizeInMB(length)
        System.out.println("path of video:- $path")
        System.out.println("size of video:- $fileSize")
        val data = arrayListOf<VideoItemModel>()
        data.add(VideoItemModel(path))
        onLoadNewData(data)
        showContent()
    }

    override fun setVideos(data: ArrayList<VideoItemModel>) {
        System.out.println("no of videos:- ${data.size}")
        onLoadNewData(data)
        // hide loading view
        if (data.isEmpty()) {
            // show error view
            mErrorView.text = "No Video Found"
            showError()
        } else {
            showContent()
        }
    }

    private fun getFileSizeInMB(bytes: Long): String {
        val kilobyte: Long = 1024
        val megabyte = kilobyte * 1024
        val gigabyte = megabyte * 1024
        return when (bytes) {
            in 0 until kilobyte -> "$bytes B"
            in kilobyte until megabyte -> (bytes / kilobyte).toString() + " KB"
            in megabyte until gigabyte -> (bytes / megabyte).toString() + " MB"
            else -> "$bytes Bytes"
        }
    }

    inner class MyAdapter : RecyclerViewAdapter<VideoItemModel>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
            return RecyclerViewHolder(VideoItemView(parent.context))
        }

        override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
            val item = get(position)
            holder.let {
                val textView = it.convertView as VideoItemView
                textView.setOnClickListener(object : SingleClick() {
                    override fun onSingleClick(view: View) {
                        playVideo(item)
                    }
                })
                textView.setModel(item)
            }
        }
    }

    private fun playVideo(item: VideoItemModel) {
        //val i = Intent(Intent.ACTION_VIEW)
        val i = Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        val mediaFile = File(item.mFile)
        val authority = App.context().applicationContext.packageName + ".provider"
        val videoUri = FileProvider.getUriForFile(App.context(), authority, mediaFile)
        i.setDataAndType(videoUri, "video/*")
        i.flags = FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION
        val componentName = i.resolveActivity(App.context().packageManager)
        if (componentName != null) {
            //this.startActivity(i)
            VideoPlayView.newInstance(mediaFile.absolutePath).show(childFragmentManager, "player")
        }else{
            showToast("Failed to play video")
            VideoPlayView.newInstance(mediaFile.absolutePath).show(childFragmentManager, "player")
        }
    }

    var mCompositeDisposable: CompositeDisposable? = null

    private fun unSubscribe() {
        mCompositeDisposable?.dispose()
    }

    private fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        } else {
            mCompositeDisposable?.add(disposable)
        }
    }

    private fun startListen() {
        addSubscribe(
            RxBus.toObserverable(Event.NewRecord::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { event ->
                    showContent()
                    mListAdapter.add(VideoItemModel(event.filePath))
                    mListAdapter.notifyDataSetChanged()
                })
    }

    private fun stopListen() {
        unSubscribe()
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            if (activity is MainView) {
            }
        }
        startListen()
    }

    override fun onPause() {
        super.onPause()
        stopListen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopListen()
    }
}