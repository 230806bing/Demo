package com.example.demo.pushdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.databinding.ActivityPushBinding

class PushActivity : AppCompatActivity() {
    var pushEntities:ArrayList<PushEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push)
        val rvPushMain:RecyclerView = findViewById(R.id.rv_push_main)
        val layout: LinearLayoutManager = LinearLayoutManager(this)
//        layout.stackFromEnd = true
        layout.reverseLayout = true
        rvPushMain.layoutManager = layout
        initData()
        rvPushMain.adapter = PushAdapter(pushEntities)
    }

    private fun initData() {
        val pushEntity1:PushEntity = PushEntity("星期二 下午 5:20",R.drawable.ic_push_portrait,R.drawable.ic_push_certification,"@Ruby今天放假","赢取奖励快来，还是要提醒大家提醒",null,true,0)
        val pushEntity2:PushEntity = PushEntity("星期三 下午 5:20",R.drawable.ic_push_portrait,R.drawable.ic_push_certification,"@Ruby今天放假","赢取奖励快来，还是要提醒大家提醒大家提醒大家提醒吃瓜吃瓜吃瓜吃瓜吃瓜",null,true,0)
        val pushEntity3:PushEntity = PushEntity("星期四 下午 5:20",R.drawable.ic_push_portrait,null,"@Ruby今天休息","[图片]\uD83C\uDF49#魔法觉醒吃动员\uD83D\uDC49\uD83D\uDD17网页链接直达网页链接直达网页链接直达网页链接直达",R.drawable.iv_push_pic,false,1)
        val pushEntity4:PushEntity = PushEntity("星期五 下午 5:20",R.drawable.ic_push_portrait,R.drawable.ic_push_certification,"@Ruby今天睡觉","[视频]@魔法觉醒瓜王 更新吃瓜动员...",R.drawable.ic_push_vedio,true,2)
        val pushEntity5:PushEntity = PushEntity("昨天 下午 5:20",R.drawable.ic_push_portrait,null,"@Ruby今天喝什么呢","[视频]",R.drawable.ic_push_vedio,false,2)
        val pushEntity6:PushEntity = PushEntity("昨天 下午 7:20",R.drawable.ic_push_portrait,null,"@汤汤今天想吃点好的","[长文]哈利波特迷集结",R.drawable.ic_push_vedio,false,3)
        val pushEntity7:PushEntity = PushEntity("昨天 下午 7:20",R.drawable.ic_push_portrait,null,"@茹今天想喝什","[长文]遍地火灰蛇不用怕神火赫敏速速带堂娃娃哇",R.drawable.ic_push_vedio,false,3)
        pushEntities.clear()
        pushEntities.add(pushEntity1)
        pushEntities.add(pushEntity2)
        pushEntities.add(pushEntity3)
        pushEntities.add(pushEntity4)
        pushEntities.add(pushEntity5)
        pushEntities.add(pushEntity6)
        pushEntities.add(pushEntity7)



    }
}