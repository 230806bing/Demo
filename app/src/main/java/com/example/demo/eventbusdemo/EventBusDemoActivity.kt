package com.example.demo.eventbusdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.demo.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusDemoActivity : AppCompatActivity() {
    private var mButton:Button ?=null
    private var mTextView:TextView ?=null
    private val context:Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bus_demo)
        mButton = findViewById(R.id.button)
        mTextView = findViewById(R.id.textView)
        mTextView!!.text = "疯狂星期四"
        EventBus.getDefault().register(this)
        jumpActivity()
    }

    private fun jumpActivity() {
        mButton!!.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view: View?) {
                val intent:Intent = Intent(context,AnoActivity::class.java)
                startActivity(intent)
            }
        })
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent){
        mTextView!!.text = messageEvent.message
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }
}