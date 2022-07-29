package com.example.demo.eventbusdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.demo.R
import org.greenrobot.eventbus.EventBus

class AnoActivity : AppCompatActivity() {
    private var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ano)
        button = findViewById(R.id.button2)
        jumpActivity()
    }

    private fun jumpActivity() {
        button!!.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                EventBus.getDefault().post(MessageEvent("KFC"))
                finish()
            }
        })
    }
}