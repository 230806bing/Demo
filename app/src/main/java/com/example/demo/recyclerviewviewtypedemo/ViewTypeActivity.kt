package com.example.demo.recyclerviewviewtypedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R

class ViewTypeActivity : AppCompatActivity() {
    var dataBeans:ArrayList<DataBean> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_type)
        val rvContent:RecyclerView = findViewById(R.id.rv_content)
        rvContent.layoutManager = LinearLayoutManager(this)
        initData()
        rvContent.adapter = MyAdapter(dataBeans)
    }

    private fun initData(){
        val databean1:DataBean = DataBean("这是内容内容内容内容内容1111",R.drawable.ic_push_portrait,2)
        val databean2:DataBean = DataBean("这是标题111111111",R.drawable.ic_push_portrait,"内容内容内容内容内容内容",1)
        val databean3:DataBean = DataBean("这是标题222222222",R.drawable.ic_push_portrait,"内容内容内容内容内容",1)
        val databean4:DataBean = DataBean("这是内容内容内容内容内容2222",R.drawable.ic_push_portrait,2)
        dataBeans.add(databean1)
        dataBeans.add(databean2)
        dataBeans.add(databean3)
        dataBeans.add(databean4)
    }
}