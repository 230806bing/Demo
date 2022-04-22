package com.example.demo.timelinedemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.demo.R;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.timelinedemo
 * Author：陈冰
 * Date：2022/3/27 20:31
 */
public class TimelineLayout extends LinearLayout {
    private Context mContext;

    private int mLineMarginLeft;
    private int mLineMarginTop;
    private int mLineStrokeWidth;
    private int mLineColor;


    private Paint mLinePaint;  //线的画笔

    //第一个点的位置
    private int mFirstX;
    private int mFirstY;
    //最后一个图标的位置
    private int mLastX;
    private int mLastY;

    public TimelineLayout(Context context) {
        this(context,null);
    }

    public TimelineLayout(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TimelineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimelineLayout);
        mLineMarginLeft = ta.getDimensionPixelOffset(R.styleable.TimelineLayout_line_margin_left, 10);
        mLineMarginTop = ta.getDimensionPixelOffset(R.styleable.TimelineLayout_line_margin_top, 0);
        mLineStrokeWidth = ta.getDimensionPixelOffset(R.styleable.TimelineLayout_line_stroke_width, 8);
        mLineColor = ta.getColor(R.styleable.TimelineLayout_line_color, 0xffeb6c65);


        ta.recycle();

        setWillNotDraw(false);
        initView(context);

    }

    private void initView(Context context) {
        this.mContext = context;

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineStrokeWidth);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTimeline(canvas);
    }

    private void drawTimeline(Canvas canvas) {
        int childCount = getChildCount();

        if (childCount > 0) {
            if (childCount > 1) {
                //大于1，证明至少有2个，也就是第一个和第二个之间连成线，第一个和最后一个分别有点和icon
                drawFirstPoint(canvas);
                drawLine(canvas);
                drawBetweenLine(canvas);
            } else if (childCount == 1) {
                drawLine(canvas);
            }
        }
    }

    private void drawLine(Canvas canvas) {
        View child = getChildAt(getChildCount() - 1);
        if (child != null) {
            int top = child.getTop();
            mLastX = mLineMarginLeft;
            mLastY = top + child.getPaddingTop() + mLineMarginTop;
            //画图
            canvas.drawLine(mLastX,mLastY,mLastX+120,mLastY,mLinePaint);
        }
    }

    private void drawBetweenLine(Canvas canvas) {
        //从开始的点到最后的图标之间，画一条线
        canvas.drawLine(mFirstX, mFirstY-4, mLastX, mLastY+4, mLinePaint);
        for (int i = 0; i < getChildCount() - 1; i++) {
            //画圆
            int top = getChildAt(i).getTop();
            int y = top + getChildAt(i).getPaddingTop() + mLineMarginTop;
            canvas.drawLine(mFirstX,y,mFirstX+120,y,mLinePaint);
        }
    }

    private void drawFirstPoint(Canvas canvas) {
        View child = getChildAt(0);
        if (child != null) {
            int top = child.getTop();
            mFirstX = mLineMarginLeft;
            mFirstY = top + child.getPaddingTop() + mLineMarginTop;
            //画圆
            canvas.drawLine(mFirstX,mFirstY,mFirstX+120,mFirstY,mLinePaint);
        }

    }

    public int getLineMarginLeft() {
        return mLineMarginLeft;
    }

    public void setLineMarginLeft(int lineMarginLeft) {
        this.mLineMarginLeft = lineMarginLeft;
        invalidate();
    }

}
