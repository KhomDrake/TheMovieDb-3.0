package com.vlv.imperiya.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import com.vlv.imperiya.R

class ImperiyaToolbarView : Toolbar {

    private var _searchView: ImperiyaSearchView? = null

    val searchView: ImperiyaSearchView?
        get() = _searchView

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        layoutParams = LinearLayoutCompat.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        setTitleTextAppearance(context, R.style.TheMovieDb_Style_Text_Title)
        setNavigationIcon(R.drawable.ic_back)
    }

    fun setTitle(text: String) {
        title = text
    }

    fun addSearchView() {
        val search = ImperiyaSearchView(context)
        search.layoutParams = LinearLayoutCompat.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        _searchView = search
        addView(search)
    }

}