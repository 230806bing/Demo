package com.example.demo.dragdemo

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/15
 *     desc   :
 */
interface IItemTouchHelperAdapter {
    /**
     * 移动时调用
     */
    fun onItemMove(fromPosition: Int, toPosition: Int)

    /**
     * 侧滑时调用
     */
    fun onItemDismiss(position:Int)
}