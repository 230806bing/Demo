package com.example.demo.autotextdemo

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/09/30
 *     desc   :
 */
class AutoWidthTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context,attributeSet)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun splitText(charSequence: CharSequence): String {
        if (TextUtils.isEmpty(charSequence)) return ""
        var splitTextList: MutableList<StringBuilder> = ArrayList()
        val singleLineWidth: Int = measuredWidth - paddingLeft - paddingRight
        var currentSingleLineWidth = 0.0
        var lineSb = StringBuilder()
        val length = charSequence.length;
        for (i in 0 until length) {
            val textChar = charSequence[i]
            currentSingleLineWidth += getSingleCharWidth(textChar)
            if (currentSingleLineWidth > singleLineWidth) {
                lineSb.append(" ")
                splitTextList.add(lineSb)
                lineSb = StringBuilder()
                currentSingleLineWidth = 0.0
                i downTo 0
            } else {
                lineSb.append(textChar)
                if (i == length - 1) {
                    splitTextList.add(lineSb)
                }
            }
        }
        val splitSb = StringBuilder()
        val maxLines = maxLines
        val hasMore = splitTextList.size > maxLines
        if (hasMore) {
            splitTextList = splitTextList.subList(0, maxLines)
        }
        for (sb: StringBuilder in splitTextList) {
            splitSb.append(sb)
        }
        if (hasMore) {
            val length = splitSb.length
            val moreDots = "..."
            splitSb.replace(length - moreDots.length + 1, length - 1, moreDots)
        }
        return splitSb.toString()
    }


    private fun getSingleCharWidth(singleText: Char): Float {
        val width = FloatArray(1)
        paint.getTextWidths(charArrayOf(singleText), 0, 1, width)
        return width[0]
    }

}