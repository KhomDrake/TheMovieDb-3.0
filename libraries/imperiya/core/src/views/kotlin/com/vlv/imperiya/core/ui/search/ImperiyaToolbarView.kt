package com.vlv.imperiya.core.ui.search

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.search.ImperiyaSearchView

class ImperiyaToolbarView(context: Context, attrs: AttributeSet?) : Toolbar(context, attrs) {

    private var _searchView: ImperiyaSearchView

    val searchView: ImperiyaSearchView
        get() = _searchView

    init {
        _searchView = addSearchView()
        val dimenLeft = context.resources.getDimension(R.dimen.imperiya_carousel_margin).toInt()
        val dimenRight = context.resources.getDimension(R.dimen.imperiya_carousel_margin2).toInt()

        setContentInsetsAbsolute(dimenLeft, dimenRight)
        contentInsetStartWithNavigation = 0
        isFocusableInTouchMode = false

        setTitleTextAppearance(context, R.style.Imperiya_Style_Text_Title)
        setupWithAttributes(context, attrs)
    }

    private fun setupWithAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.ImperiyaToolbarView)

        val showIcon = attrs.getBoolean(R.styleable.ImperiyaToolbarView_with_back_icon, true)

        if(showIcon) {
            setNavigationIcon(R.drawable.ic_back)
        }

        attrs.recycle()
    }

    fun setTitle(text: String) {
        title = text
    }

    private fun addSearchView() : ImperiyaSearchView {
        val search = ImperiyaSearchView(context)
        _searchView = search
        addView(
            search, -1,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )
        return search
    }

}