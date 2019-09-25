package com.schn.camera2019.ui.player

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.google.android.exoplayer2.ui.PlayerView
import com.schn.camera2019.R
import com.schn.camera2019.base.dialog.BaseMvpDialogFragment
import com.schn.camera2019.ui.player.job.Mp4VideoPlayJob
import com.schn.camera2019.ui.player.job.VideoPlayJob

class VideoPlayView : BaseMvpDialogFragment<VideoPlayContract.View, VideoPlayContract.Presenter>(),
    VideoPlayContract.View,
    VideoPlayJob.Listener {


    companion object {
        fun newInstance(filePath: String): VideoPlayView {
            val view = VideoPlayView()
            val bundle = Bundle()
            bundle.putString("file", filePath)
            view.arguments = bundle
            return view
        }
    }

    override fun onBuffer() {
        progressView?.visibility = View.VISIBLE
    }

    override fun onPlaying() {
        progressView?.visibility = View.GONE
    }

    override fun onError() {
        showToast("Error in playing video")
    }

    override var TAG: String = "VideoPlayView.class"

    override fun createPresenter(): VideoPlayContract.Presenter {
        return VideoPlayPresenter()
    }

    override fun findMvpView(): VideoPlayContract.View {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_play
    }

    private var mPlayerView: PlayerView? = null

    private lateinit var mVideoPlayJob: Mp4VideoPlayJob

    private var progressView: LinearLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filePath = arguments?.getString("file")
        this.mPlayerView = view.findViewById<PlayerView>(R.id.player_view)
        this.progressView = view.findViewById(R.id.loadingView)
        mVideoPlayJob = Mp4VideoPlayJob(view.context)
        mVideoPlayJob.init(mPlayerView, this)
        val bundle = Bundle()
        bundle.putString("FILE_PATH_KEY", filePath)
        mVideoPlayJob.playResource(bundle)
    }

}