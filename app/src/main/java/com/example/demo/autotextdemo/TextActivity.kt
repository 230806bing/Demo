package com.example.demo.autotextdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.R

class TextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)
        var textView: AutoWidthTextView = findViewById(R.id.tv_feed_push_video_text_content)
        var textView1: AutoWidthTextView = findViewById(R.id.tv_feed_push_video_text_content1)
        textView1.post{
            val str = textView1.splitText("[长文]大家开始恢复健康和地方几乎都是看了凤凰大厦路口刚好放假了考虑点击链接可v觉得还是离开感觉我离开从开始到后来开始觉得分开来说v克里斯蒂离开")
            textView1.text = str
        }
    }
}