package com.vlv.common.ui

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager


class AutoMeasureViewPager : ViewPager {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val h = child.measuredHeight
            if (h > height) height = h
        }

        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
            height,
            MeasureSpec.EXACTLY
        )

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

}