package com.vlv.imperiya.ui.search

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.vlv.imperiya.R

class ImperiyaToolbarView(context: Context, attrs: AttributeSet?) : Toolbar(context, attrs) {

    private var _searchView: ImperiyaSearchView

    val searchView: ImperiyaSearchView
        get() = _searchView

    init {
        _searchView = addSearchView()
        val dimen = context.resources.getDimension(R.dimen.imperiya_carousel_margin).toInt()

        setContentInsetsAbsolute(dimen, dimen)
        contentInsetStartWithNavigation = 0
        isFocusableInTouchMode = false

        setTitleTextAppearance(context, R.style.TheMovieDb_Style_Text_Title)
        setNavigationIcon(R.drawable.ic_back)
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