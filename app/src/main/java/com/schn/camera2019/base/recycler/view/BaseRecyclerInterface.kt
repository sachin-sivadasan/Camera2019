package com.schn.camera2019.base.recycler.view

interface BaseRecyclerInterface<M> {

    fun onLoadNewData(data: ArrayList<M>)
    fun onLoadFinished()
    fun onLoadError()

}