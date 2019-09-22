package com.schn.camera2019.base.recycler.adapter

import android.view.ViewGroup

abstract class RecyclerViewAdapter<E> : BaseRecyclerViewAdapter<E>() {
    override fun getItemCount(): Int {
        return size()
    }
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder
    abstract override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int)
}