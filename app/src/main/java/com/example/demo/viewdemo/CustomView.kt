package com.example.demo.viewdemo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Scroller
import java.util.jar.Attributes

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/21
 *     desc   :
 */
class CustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    init {
    }



    var lastX: Int? = null
    var lastY: Int? = null
    var mScroller:Scroller = Scroller(context)
    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()){
            (parent as View).scrollTo(mScroller.currX,mScroller.currY)
            invalidate()
        }
    }

    fun smoothScrollTo(destX:Int,destY:Int){
        var scrollX:Int = scrollX
        var delta = destX - scrollX
        mScroller.startScroll(scrollX,0,delta,0,2000)
        invalidate()
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var x: Int = event!!.x.toInt()
        var y: Int = event!!.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = x;
                lastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                var offsetX: Int = x - lastX!!
                var offsetY: Int = y - lastY!!
//                使用layout进行放置新位置
//                layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
//                左右进行偏移
//                offsetLeftAndRight(offsetX)
//                前后进行偏移
//                offsetTopAndBottom(offsetY)
//                LayoutParams(1)
//                var layoutParams:LinearLayout.LayoutParams = getLayoutParams() as LinearLayout.LayoutParams
//                layoutParams.leftMargin = left + offsetX
//                layoutParams.topMargin =top+offsetY
//                setLayoutParams(layoutParams)
//                LayoutParams(2)
//                var layoutParams:ViewGroup.MarginLayoutParams = getLayoutParams() as ViewGroup.MarginLayoutParams
//                layoutParams.leftMargin = left + offsetX
//                layoutParams.topMargin =top+offsetY
//                setLayoutParams(layoutParams)
//                (parent as View).scrollBy(-offsetX,-offsetY)



            }
        }
        return true
    }

}