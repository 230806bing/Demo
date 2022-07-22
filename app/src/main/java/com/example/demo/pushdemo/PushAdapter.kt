package com.example.demo.pushdemo

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.ItemPushOnlyTextBinding
import com.example.demo.databinding.ItemPushPicTextBinding
import com.example.demo.databinding.ItemPushVedioTextBinding
import com.example.demo.pushdemo.JumpActivity as JumpActivity1

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/19
 *     desc   :
 */
class PushAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val ONLY_TEXT_TYPE: Int = 0
        val TEXT_PIC_TYPE: Int = 1
        val TEXT_VIDEO_TYPE: Int = 2
        val LONG_TEXT_TYPE: Int = 3
        fun stringLength(value: String?): Int {
            var valueLength: Int = 0
            var chinese: String = "[\u4e00-\u9fa5]"
            for (i in 0..value!!.length - 1) {
                var temp: String = value!!.substring(i, i + 1)
                if (temp.matches(chinese.toRegex())) {
                    valueLength += 2
                } else {
                    valueLength += 1
                }
            }
            return valueLength
        }
    }

    var pushEntities: ArrayList<PushEntity> = ArrayList()

    constructor(pushEntities: ArrayList<PushEntity>) : this() {
        this.pushEntities = pushEntities
    }

    class OnlyTextViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPushOnlyTextBinding: ItemPushOnlyTextBinding? = DataBindingUtil.getBinding(itemView)
        fun setData(pushEntity: PushEntity) {
            itemPushOnlyTextBinding!!.tvPushTime.text = pushEntity.time
            pushEntity.portraitLocal?.let { itemPushOnlyTextBinding.ivPushPortrait.setImageResource(it) }
            if (pushEntity.isCertification == true) {
                itemPushOnlyTextBinding.ivPushCertification.visibility = View.VISIBLE
            }
            if (PushAdapter.stringLength(pushEntity.title) > 13) {
                itemPushOnlyTextBinding.tvPushTitleId.maxEms = 6
                itemPushOnlyTextBinding.tvPushTitleId.setSingleLine()
            } else {
                itemPushOnlyTextBinding.tvPushTitleId.maxEms = 7
            }
            itemPushOnlyTextBinding.tvPushTitleId.text = pushEntity.title
            itemPushOnlyTextBinding.tvPushTitleId.setOnClickListener(object : View.OnClickListener{
                override fun onClick(view: View?) {
                    itemView.context.startActivity(Intent(itemView.context, JumpActivity1::class.java))
                }
            })
            itemPushOnlyTextBinding.ivPushContentBackground.setOnClickListener(object :View.OnClickListener{
                override fun onClick(view: View?) {
                    itemView.context.startActivity(Intent(itemView.context, JumpActivity1::class.java))
                    (itemView.context as Activity).overridePendingTransition(R.anim.left_to_right_in,R.anim.left_to_right_out)

                }
            })
            itemPushOnlyTextBinding.tvPushContent.text = pushEntity.content
        }
    }

    class TextPicViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPushPicTextBinding: ItemPushPicTextBinding? = DataBindingUtil.getBinding(itemView)

        fun setData(pushEntity: PushEntity) {
            itemPushPicTextBinding!!.tvPushTime.text = pushEntity.time
            pushEntity.portraitLocal?.let {
                itemPushPicTextBinding.ivPushPortrait.setImageResource(
                    it
                )
            }
            if (pushEntity.isCertification == true) {
                itemPushPicTextBinding.ivPushCertification.visibility = View.VISIBLE
            }
            if (PushAdapter.stringLength(pushEntity.title) > 13) {
                itemPushPicTextBinding.tvPushTitleId.maxEms = 6
                itemPushPicTextBinding.tvPushTitleId.setSingleLine()
            } else {
                itemPushPicTextBinding.tvPushTitleId.maxEms = 7
            }
            itemPushPicTextBinding.tvPushTitleId.text = pushEntity.title
            itemPushPicTextBinding.tvPushTitleId.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    itemView.context.startActivity(Intent(itemView.context, JumpActivity1::class.java))
                }
            })
            itemPushPicTextBinding.ivPushContentBackground.setOnClickListener(object :View.OnClickListener{
                override fun onClick(view: View?) {
                    itemView.context.startActivity(Intent(itemView.context, JumpActivity1::class.java))
                    (itemView.context as Activity).overridePendingTransition(R.anim.left_to_right_in,R.anim.left_to_right_out)

                }
            })
            pushEntity.picLocal?.let { itemPushPicTextBinding.ivPushPic.setImageResource(it) }
            itemPushPicTextBinding.tvPushContent.text = pushEntity.content
        }
    }

    class TextVideoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPushVedioTextBinding: ItemPushVedioTextBinding? =
            DataBindingUtil.getBinding(itemView)

        fun setData(pushEntity: PushEntity) {
            itemPushVedioTextBinding!!.tvPushTime.text = pushEntity.time
            pushEntity.portraitLocal?.let {
                itemPushVedioTextBinding.ivPushPortrait.setImageResource(
                    it
                )
            }
            if (pushEntity.isCertification == true) {
                itemPushVedioTextBinding.ivPushCertification.visibility = View.VISIBLE
            }
            if (PushAdapter.stringLength(pushEntity.title) > 13) {
                itemPushVedioTextBinding.tvPushTitleId.maxEms = 6
                itemPushVedioTextBinding.tvPushTitleId.setSingleLine()
            } else {
                itemPushVedioTextBinding.tvPushTitleId.maxEms = 7
            }
            itemPushVedioTextBinding.tvPushTitleId.text = pushEntity.title
            itemPushVedioTextBinding.tvPushTitleId.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    itemView.context.startActivity(Intent(itemView.context, JumpActivity1::class.java))
                }
            })
            itemPushVedioTextBinding.ivPushContentBackground.setOnClickListener(object :View.OnClickListener{
                override fun onClick(view: View?) {
                    itemView.context.startActivity(Intent(itemView.context, JumpActivity1::class.java))
                    (itemView.context as Activity).overridePendingTransition(R.anim.left_to_right_in,R.anim.left_to_right_out)

                }
            })
            pushEntity.picLocal?.let { itemPushVedioTextBinding.ivPushVideoPic.setImageResource(it) }
            if (pushEntity.type == LONG_TEXT_TYPE) {
                itemPushVedioTextBinding.ivPushVideo.visibility = View.INVISIBLE
            } else {
                itemPushVedioTextBinding.ivPushVideo.setImageResource(R.drawable.ic_push_stop)
            }
            itemPushVedioTextBinding.tvPushContent.text = pushEntity.content
        }
    }

    class DefaultViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        return when (viewType) {
//            ONLY_TEXT_TYPE -> OnlyTextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_push_only_text,parent,false))
//            TEXT_PIC_TYPE -> TextPicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_push_pic_text,parent,false))
//            TEXT_VIDEO_TYPE -> TextVideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_push_vedio_text,parent,false))
//            LONG_TEXT_TYPE -> TextVideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_push_vedio_text,parent,false))
            ONLY_TEXT_TYPE -> {
                val itemPushOnlyTextBinding: ItemPushOnlyTextBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_push_only_text,
                    parent,
                    false
                )
                OnlyTextViewHolder(itemPushOnlyTextBinding.root)
            }
            TEXT_PIC_TYPE -> {
                val itemPushPicTextBinding: ItemPushPicTextBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_push_pic_text,
                    parent,
                    false
                )
                TextPicViewHolder(itemPushPicTextBinding.root)
            }
            TEXT_VIDEO_TYPE -> {
                val itemPushVedioTextBinding: ItemPushVedioTextBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_push_vedio_text,
                    parent,
                    false
                )
                TextVideoViewHolder(itemPushVedioTextBinding.root)
            }
            LONG_TEXT_TYPE -> {
                val itemPushLongTextBinding: ItemPushVedioTextBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_push_vedio_text,
                    parent,
                    false
                )
                TextVideoViewHolder(itemPushLongTextBinding.root)
            }
            else -> DefaultViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OnlyTextViewHolder -> holder.setData(pushEntities!!.get(position))
            is TextPicViewHolder -> holder.setData(pushEntities!!.get(position))
            is TextVideoViewHolder -> holder.setData(pushEntities!!.get(position))
        }
    }

    override fun getItemCount(): Int {
        return pushEntities?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return pushEntities!!.get(position).type!!
    }


}


