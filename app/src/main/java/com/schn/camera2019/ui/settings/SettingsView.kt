package com.schn.camera2019.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import com.schn.camera2019.R
import com.schn.camera2019.base.dialog.BaseMvpDialogFragment
import com.schn.camera2019.util.ConstantUtil
import com.schn.camera2019.util.LogUtils
import com.schn.camera2019.util.PreferenceUtil
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingsView : BaseMvpDialogFragment<SettingsContract.View, SettingsContract.Presenter>(), SettingsContract.View {

    override var TAG: String = "SettingsView.class"
    private var min = 1
    override fun createPresenter(): SettingsContract.Presenter {
        return SettingsPresenter()
    }

    override fun findMvpView(): SettingsContract.View {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backIv = toolbar.findViewById<ImageView>(R.id.back)
        backIv.setOnClickListener {
            dismiss()
        }


        val seekBar = view.findViewById(R.id.thresholdSb) as SeekBar
        seekBar.max = 10

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var lastProgress = 10
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                assert(progress in 0..10)
                lastProgress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                LogUtils.d(TAG,"progress $lastProgress")
                val threshold = (min + lastProgress) * 0.5
                setThreshold(threshold)
            }
        })

        val default = PreferenceUtil.getString(ConstantUtil.EXTRA_KEY_THRESHOLD, "0.5")
        LogUtils.d(TAG,"default threshold $default")
        val defaultDouble = default.toDouble()
        val progress = ((defaultDouble / 0.5) - min).toInt()
        seekBar.progress = progress
        updateUI(defaultDouble.toString())
        LogUtils.d(TAG,"default progress $progress")

    }

    private fun updateUI(progress: String) {
        valueTv.text = progress
    }

    // value set for detecting invalid movement that will be
    // reflected on the camera view.
    private fun setThreshold(threshold: Double) {
        LogUtils.d(TAG,"threshold $threshold")
        updateUI(threshold.toString())
        PreferenceUtil.put(ConstantUtil.EXTRA_KEY_THRESHOLD, threshold.toString())
    }
}