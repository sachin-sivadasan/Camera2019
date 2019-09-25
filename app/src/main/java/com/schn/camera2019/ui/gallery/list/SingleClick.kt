package com.schn.camera2019.ui.gallery.list

import android.os.SystemClock
import android.view.View

public abstract class SingleClick : View.OnClickListener {

    /**
     * The time between two consecutive click events, in milliseconds.
     */
    private val MIN_CLICK_INTERVAL: Long = 1000

    private var mLastClickTime: Long = 0

    /**
     * Custom [View.OnClickListener] preventing unwanted double click.
     *
     * @param view View where double click is undesirable
     */
    abstract fun onSingleClick(view: View)

    override fun onClick(v: View) {

        if (SystemClock.uptimeMillis() - mLastClickTime < MIN_CLICK_INTERVAL)
            return
        mLastClickTime = SystemClock.uptimeMillis()
        onSingleClick(v)
    }

}