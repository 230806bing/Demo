package com.example.demo.dragdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.kotlinrecyclerviewdemo.MyAdapter

class DragActivity : AppCompatActivity() {

    val mList = arrayListOf(
        "aa",
        "bb",
        "cc",
        "dd"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag)
        val listView:RecyclerView = findViewById(R.id.rv_main)
        val adapter:DragRecyclerViewAdapter = DragRecyclerViewAdapter(mList)
        listView.layoutManager = LinearLayoutManager(this)
        listView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        listView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(listView)
    }
}