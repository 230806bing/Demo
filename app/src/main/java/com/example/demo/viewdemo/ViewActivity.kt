package com.example.demo.viewdemo

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.demo.R

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val customView:CustomView = findViewById(R.id.cv_custom)
        customView.smoothScrollTo(-400,0)
//        customView.animation = AnimationUtils.loadAnimation(this,R.anim.translate)
//        属性动画
//        ObjectAnimator.ofFloat(customView,"translationX",0f,300f).setDuration(1000).start();
            }
}