package com.example.demo.rippleanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo.R;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.rippleanimationdemo
 * Author：陈冰
 * Date：2022/4/2 10:49
 */
public class RippleView extends View {
    private static final String TAG = "RippleView";
    private Paint paint;

    private float startX;
    private float startY;
    private float radius;
    private onRippleListener onRippleListener;


    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setColor(Color.parseColor("#F34235"));
    }

    // 通过 ObjectAnimator 来开启动画，需要反射方式去设置 radius，因此要 setter() 方法
    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void startRippleAnimation(RipplePosition ripplePosition) {
        onRippleListener.rippleState(RippleState.RIPPLE_START);
        this.startX = ripplePosition.getStartX();
        this.startY = ripplePosition.getStartY();
        float side = (float) Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2));
        Log.e(TAG, side+"11111");
        @SuppressLint("ObjectAnimatorBinding")
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "radius", 0, side);
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onRippleListener.rippleState(RippleState.RIPPLE_END);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, radius+"");
        canvas.drawCircle(startX, startY, radius, paint);
    }

    public interface onRippleListener {
        void rippleState(int state);
    }

    public void addOnRippleListener(RippleView.onRippleListener onRippleListener) {
        this.onRippleListener = onRippleListener;
    }

}
