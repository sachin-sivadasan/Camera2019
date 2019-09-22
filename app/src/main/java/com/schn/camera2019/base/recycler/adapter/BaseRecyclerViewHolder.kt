package com.schn.camera2019.base.recycler.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Mfluid on 12/13/2018.
 */
abstract class BaseRecyclerViewHolder : RecyclerView.ViewHolder {
    val convertView: View

    constructor(view: View) : super(view) {
        this.convertView = view
    }
}