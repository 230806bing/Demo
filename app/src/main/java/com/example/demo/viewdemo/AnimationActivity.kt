package com.example.demo.viewdemo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.demo.R

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        var ivAnimation:ImageView = findViewById(R.id.iv_animation)
        var mObjectAnimator1:ObjectAnimator = ObjectAnimator.ofFloat(ivAnimation,"translationX",200f)
        var mObjectAnimator2:ObjectAnimator = ObjectAnimator.ofFloat(ivAnimation,"rotationX",2000f)
        var animationSet:AnimatorSet = AnimatorSet()
        animationSet.setDuration(1000)
        animationSet.play(mObjectAnimator1).with(mObjectAnimator2)
        animationSet.start()
//        mObjectAnimator.setDuration(3000)
//        mObjectAnimator.start()
//        var valueAnimator:ValueAnimator = ValueAnimator.ofFloat(0f,100f,200f,300f,400f)
//        valueAnimator.setTarget(ivAnimation)
//        valueAnimator.setDuration(2000).start()
//        valueAnimator.addUpdateListener {object:ValueAnimator.AnimatorUpdateListener {
//            override fun onAnimationUpdate(p0: ValueAnimator?) {
//                var float:Float = p0!!.getAnimatedValue() as Float
//            }
//        }
//
//        }

    }
}