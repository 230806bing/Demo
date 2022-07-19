package com.example.demo.dragdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import java.util.*
import kotlin.collections.ArrayList

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/15
 *     desc   :
 */
class DragRecyclerViewAdapter constructor(mList:ArrayList<String>) : IItemTouchHelperAdapter,
    RecyclerView.Adapter<DragRecyclerViewAdapter.ViewHolder>() {

     class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var tv_main:TextView = itemView.findViewById(R.id.item)
         var tv_delete:TextView = itemView.findViewById(R.id.tv_text)
         var iv_eye:ImageView = itemView.findViewById(R.id.iv_img)
    }

    private var mList: ArrayList<String> = mList

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(mList,fromPosition,toPosition)
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder:ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.drag_delete_layout,parent,false))
        return viewHolder
    }


    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder:ViewHolder = holder
        holder.tv_main.text = mList.get(position)
    }


}