package com.schn.camera2019.base.recycler.model

/**
 * Base class for adapter.
 */
abstract class BaseObjectAdapter {

    // returns size of the data
    abstract fun size(): Int

    // returns data at a certain position
    abstract fun get(position: Int): Any

    // default id
    private val NO_ID: Long = -1

    // returns id
    open fun id(): Long {
        return NO_ID
    }
}