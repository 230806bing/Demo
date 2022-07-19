package com.example.demo.kotlinrecyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.demo.R

class KotlinRecyclerViewActivity : AppCompatActivity() {
    val items= listOf(
        "给初学者的RxJava2.0教程（七）: Flowable",
        "Android之View的诞生之谜",
        "Android之自定义View的死亡三部曲之Measure",
        "Using ThreadPoolExecutor in Android ",
        "Kotlin 泛型定义与 Java 类似，但有着更多特性支持。",
        "Android异步的姿势，你真的用对了吗？",
        "Android 高质量录音库。",
        "Android 边缘侧滑效果，支持多种场景下的侧滑退出。"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_recycler)
        val listView:RecyclerView = findViewById(R.id.rv_main)
        listView.layoutManager = LinearLayoutManager(this)
        listView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        listView.adapter = MyAdapter(items)
    }
}