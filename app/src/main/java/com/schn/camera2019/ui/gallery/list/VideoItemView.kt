package com.schn.camera2019.ui.gallery.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.schn.camera2019.R
import java.io.File

/**
 * view for video card.
 */
class VideoItemView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var mContext: Context

    private var mNameTv: TextView?

    private var mThumbIv: ImageView?

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val mRoot = LayoutInflater.from(context).inflate(R.layout.item_video_view, this)
        mNameTv = mRoot.findViewById<TextView>(R.id.nameTv)
        mThumbIv = mRoot.findViewById<ImageView>(R.id.thumbIv)
        mContext = context
    }

    private fun setName(filePath: String) {
        mNameTv?.text = filePath.substring(filePath.lastIndexOf(File.separator) + 1)
    }

    fun setModel(model: VideoItemModel) {
        setName(model.mFile)
        val mThumbIv = mThumbIv ?: return
        Glide.with(context)
            .load(model.mFile)
            .centerCrop()
            .into(mThumbIv)
    }
}