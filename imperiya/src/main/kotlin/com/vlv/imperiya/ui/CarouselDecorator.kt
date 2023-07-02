package com.vlv.imperiya.ui

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import com.vlv.imperiya.R

class CarouselDecorator(
    @DimenRes
    private val baseline: Int = R.dimen.imperiya_carousel_margin,
    private val edgeTimeBaseline: Int = 8,
    private val percentage: Float = 1f
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val windowWidth = (parent.context.resources.displayMetrics.widthPixels * percentage)
            .toInt()

        val baselineValue = parent.context.resources.getDimension(baseline).toInt()

        if(isFirstItem(view, parent, state)) {
            outRect.left = baselineValue

        } else if(isLastItem(view, parent, state)) {
            outRect.right = baselineValue
        }
        view.layoutParams?.width = windowWidth - (baselineValue * edgeTimeBaseline)
        view.requestLayout()
    }

    private fun isFirstItem(view: View, parent: RecyclerView, state: State) = run {
        val count = parent.adapter?.itemCount ?: state.itemCount
        val viewPosition = parent.getChildAdapterPosition(view)
        viewPosition == 0
    }

    private fun isLastItem(view: View, parent: RecyclerView, state: State) = run {
        val count = parent.adapter?.itemCount ?: state.itemCount
        val viewPosition = parent.getChildAdapterPosition(view)
        viewPosition == (count - 1)
    }

}