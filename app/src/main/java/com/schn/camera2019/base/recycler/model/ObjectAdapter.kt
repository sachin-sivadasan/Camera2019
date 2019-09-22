package com.schn.camera2019.base.recycler.model

import java.util.*

open class ObjectAdapter<E : Any> : BaseObjectAdapter() {

    // list for storing data.
    var mList: MutableList<E> = mutableListOf()

    // returns size of the data
    override fun size(): Int {
        return mList.size
    }

    // returns data at position
    override fun get(position: Int): E {
        return mList[position]
    }

    // set data to the list
    fun set(data: ArrayList<E>) {
        // clear old data from the list
        mList.clear()
        // add new data to list
        mList.addAll(data)
    }

}