package com.schn.camera2019.ui.record

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.schn.camera2019.R
import com.schn.camera2019.app.App
import com.schn.camera2019.base.recycler.adapter.BaseRecyclerViewHolder
import com.schn.camera2019.base.recycler.adapter.RecyclerViewAdapter
import com.schn.camera2019.base.recycler.adapter.RecyclerViewHolder
import com.schn.camera2019.base.recycler.view.BaseRecyclerFragment
import com.schn.camera2019.permission.EasyPermission
import java.io.File

class RecordView : BaseRecyclerFragment<FrameLayout, VideoFile, RecordContract.View, RecordContract.Presenter>(),
    RecordContract.View {

    override var mListAdapter: RecyclerViewAdapter<VideoFile> = MyAdapter()

    override fun initListView() {

    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_record
    }

    override fun loadData() {
    }

    override fun setData(file: VideoFile) {}

    override fun createPresenter(): RecordContract.Presenter = RecordPresenter()

    override fun findMvpView(): RecordContract.View = this
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (EasyPermission.canRecordVideo(activity as AppCompatActivity)) {
            openCamera()
        }
    }

    private fun openCamera() {
        val absolutePath = Environment.getExternalStorageDirectory().absolutePath
        val videoName = "video_" + System.currentTimeMillis() + ".mp4"
        val videoPath = absolutePath + File.pathSeparator + videoName
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
                val absolutePath = Environment.getExternalStorageDirectory().absolutePath
                val videoName = "video_" + System.currentTimeMillis() + ".mp4"
                val videoPath = absolutePath + File.pathSeparator + videoName
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
        val data = arrayListOf<VideoFile>()
        data.add(VideoFile())
        onLoadNewData(data)
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

    inner class MyAdapter : RecyclerViewAdapter<VideoFile>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
            return RecyclerViewHolder(TextView(parent.context))
        }

        override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
            val item = get(position)
            holder.let {
                val textView = it.convertView as TextView
                textView.text = item.toString()
            }
        }
    }
}