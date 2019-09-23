package com.schn.camera2019.base.recycler.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schn.camera2019.R
import com.schn.camera2019.base.mvp.BaseContract
import com.schn.camera2019.base.mvp.BaseMvpLceView
import com.schn.camera2019.base.recycler.adapter.BaseRecyclerViewHolder
import com.schn.camera2019.base.recycler.adapter.RecyclerViewAdapter
import com.schn.camera2019.base.recycler.adapter.RecyclerViewHolder

abstract class BaseRecyclerFragment<CV : View, M : Any,
        V : BaseContract.ListView<M>,
        P : BaseContract.ListPresenter<M, V>> :
    BaseMvpLceView<CV, M, V, P>(),
    BaseContract.ListView<M>,
    BaseRecyclerInterface<M>,
    AdapterView.OnItemClickListener {

    val STATE_LOAD_DATA = 0
    val STATE_NONE = 1
    var mState = STATE_NONE

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(parent?.context, "clicked " + position, Toast.LENGTH_SHORT).show()
    }

    //@BindView(R.id.listView)
    lateinit var mRecyclerView: RecyclerView

    protected open var mListAdapter: RecyclerViewAdapter<M> = ListAdapter()

    abstract fun getLayoutID(): Int

    abstract fun initListView()

    var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView != null) {
            val parent = mRootView?.parent as ViewGroup
            parent.removeView(mRootView)
        } else {
            mRootView = inflater?.inflate(getLayoutID(), container, false)
        }
        return mRootView
    }

    override fun _onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRecyclerView = view.findViewById(R.id.listView)
        mRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        mRecyclerView.adapter = mListAdapter
        initListView()
        Log.d("test_view", "loading data")
        loadData()
    }

    override fun onLoadNewData(data: ArrayList<M>) {
        //mObjectAdapter?.set(data)
        mListAdapter.set(data)
        mListAdapter.notifyDataSetChanged()
    }

    override fun onLoadFinished() {
        mState = STATE_NONE
        showContent()
    }

    override fun onLoadError() {
        showError()
    }

    inner class ListAdapter<M> : RecyclerViewAdapter<M>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
            return RecyclerViewHolder(TextView(parent.context))
        }

        override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
            val item = get(position)
            holder.let {
                val textView = it.convertView as TextView
                textView.text = item.toString()
            }
        }
    }
}