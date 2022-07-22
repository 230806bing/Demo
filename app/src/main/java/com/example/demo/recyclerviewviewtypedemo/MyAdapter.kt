package com.example.demo.recyclerviewviewtypedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/20
 *     desc   :
 */
class MyAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object{
        val First_TYPE = 1;
        val Second_TYPE = 2;
    }
    private var dataBeans:ArrayList<DataBean> = ArrayList()
    constructor(dataBeans: ArrayList<DataBean>) : this() {
        this.dataBeans = dataBeans
    }


    class FirstViewHolder constructor(itemView:View) : RecyclerView.ViewHolder(itemView){
        var title:TextView = itemView.findViewById(R.id.tv_title)
        var portrait:ImageView = itemView.findViewById(R.id.iv_portrait)
        var content:TextView = itemView.findViewById(R.id.tv_content)
        fun setData(dataBean: DataBean){
            title.text = dataBean.title
            dataBean.portrait?.let { portrait.setImageResource(it) }
            content.text =dataBean.content
        }
    }

    class SecondViewHolder constructor(itemView:View) : RecyclerView.ViewHolder(itemView){
        var portrait:ImageView = itemView.findViewById(R.id.iv_portrait)
        var content:TextView = itemView.findViewById(R.id.tv_content)
        fun setData(dataBean: DataBean){
            dataBean.portrait?.let { portrait.setImageResource(it) }
            content.text =dataBean.content
        }
    }
    class InnerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view:View?=null
        when(viewType){
            First_TYPE -> return FirstViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_has_title,parent,false))
            Second_TYPE -> return SecondViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_no_title,parent,false))
            else -> return InnerViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return dataBeans?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FirstViewHolder -> {
                holder.setData(dataBeans!!.get(position))
            }
            is SecondViewHolder -> {
                holder.setData(dataBeans!!.get(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return dataBeans!!.get(position).type!!
    }
}