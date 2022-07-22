package com.example.demo.pushdemo

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/22
 *     desc   :
 */
class EndlessOnScrollListener() : RecyclerView.OnScrollListener() {
    var mLinearLayoutManager: LinearLayoutManager? = null
    var totalItemCount: Int? = null
    var previousTotal: Int = 0
    var visibleItemCount: Int? = null
    var firstVisibleItem:Int?=null
    var loading:Boolean = true

    constructor(linearLayoutManager: LinearLayoutManager):this(){
        this.mLinearLayoutManager = linearLayoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount =recyclerView.childCount
        totalItemCount = mLinearLayoutManager?.itemCount
    }

}