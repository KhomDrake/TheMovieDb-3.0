package com.vlv.test.matcher

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class ImageDrawableMatcher(
    private val drawableId: Int
) : BoundedMatcher<View, ImageView>(ImageView::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("Drawable without $drawableId")
    }

    override fun matchesSafely(item: ImageView?): Boolean {
        val image = item ?: return false

        return ContextCompat.getDrawable(
            image.context,
            drawableId
        ) == image.drawable
    }

}