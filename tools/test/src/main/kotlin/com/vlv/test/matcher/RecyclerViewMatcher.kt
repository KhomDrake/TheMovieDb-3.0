package com.vlv.test.matcher

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withRecyclerViewItem(
    recyclerViewId: Int
) = object : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) {
        description?.appendText("with id: $recyclerViewId");
    }

    override fun matchesSafely(item: View?): Boolean {
        val recyclerView = item as? RecyclerView ?: return false

        return recyclerView.id == recyclerViewId
    }

}

fun atPosition(
    position: Int,
    matcher: Matcher<View>,
    itemViewId: Int
) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendValue("Not found view with id $itemViewId in position $position")
    }

    override fun matchesSafely(item: RecyclerView?): Boolean {
        val recyclerView = item ?: return false

        val view = recyclerView.findViewHolderForAdapterPosition(position)?.itemView ?: return false

        val targetView = view.findViewById<View>(itemViewId)

        return matcher.matches(targetView)
    }
}


class RecyclerViewMatcherQuantityItems(
    private val quantityOfItems: Int
) : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        description?.appendValue("Not found recyclerview with quantity of items equal to $quantityOfItems")
    }

    override fun matchesSafely(item: View): Boolean {
        val recyclerView = (item as? RecyclerView) ?: return false

        return recyclerView.adapter?.itemCount == quantityOfItems
    }
}