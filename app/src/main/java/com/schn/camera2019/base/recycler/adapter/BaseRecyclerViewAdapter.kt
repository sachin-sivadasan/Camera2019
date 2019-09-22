package com.schn.camera2019.base.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Created by Mfluid on 12/18/2018.
 */
abstract class BaseRecyclerViewAdapter<E> : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    var mList: MutableList<E> = mutableListOf()

    private val NO_ID: Long = -1

    fun get(position: Int): E {
        return mList[position]
    }

    fun size(): Int {
        return mList.size
    }

    fun id(): Long {
        return NO_ID
    }

    fun set(data: ArrayList<E>) {
        mList.clear()
        mList.addAll(data)
    }
}