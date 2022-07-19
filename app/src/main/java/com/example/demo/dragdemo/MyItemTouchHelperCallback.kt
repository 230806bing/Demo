package com.example.demo.dragdemo

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.kotlinrecyclerviewdemo.MyAdapter

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/15
 *     desc   :
 */
class MyItemTouchHelperCallback constructor(mAdapter: IItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    var mAdapter: IItemTouchHelperAdapter = mAdapter
    val ICON_MAX_SIZE :Double = 50.00
    val fixedWidth :Int = 150

    /**
     * 定义可拖拽和滑动的方向
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags,swipeFlags)
    }


    /**
     * 上下拖拽回调用到
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onItemMove(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    /**
     * 左右侧滑回调用到
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.scrollX = 0
        viewHolder as DragRecyclerViewAdapter.ViewHolder
        viewHolder.tv_delete.text = "左滑删除"
        val params:FrameLayout.LayoutParams = viewHolder.iv_eye.layoutParams as FrameLayout.LayoutParams
        params.width = 150
        params.height = 150
        viewHolder.iv_eye.layoutParams = params
        viewHolder.iv_eye.visibility = View.VISIBLE

    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if (Math.abs(dX) <= getSlideLimitation(viewHolder)){
                viewHolder.itemView.scrollTo(-(dX.toInt()),0)
            }
            else if (Math.abs(dX) <= recyclerView.width /2){
                var distance:Double = (recyclerView.width/2 - getSlideLimitation(viewHolder)).toDouble()
                var factor:Double = ICON_MAX_SIZE/distance
                var diff:Double= (Math.abs(dX) - getSlideLimitation(viewHolder)) *factor
                if (diff>=ICON_MAX_SIZE)
                    diff = ICON_MAX_SIZE;
                viewHolder as DragRecyclerViewAdapter.ViewHolder
                viewHolder.tv_delete.text = ""
                viewHolder.iv_eye.visibility = View.VISIBLE
                var params:FrameLayout.LayoutParams = viewHolder.iv_eye.layoutParams as FrameLayout.LayoutParams
                params.width = (fixedWidth +diff).toInt()
                params.height = (fixedWidth +diff).toInt()
                viewHolder.iv_eye.layoutParams = params
            }
        }
        else{
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
    fun getSlideLimitation(viewHolder: RecyclerView.ViewHolder): Int{
        val viewGroup:ViewGroup = viewHolder.itemView as ViewGroup
        return viewGroup.getChildAt(1).layoutParams.width
    }
}