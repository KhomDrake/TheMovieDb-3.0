package com.vlv.imperiya.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.R

class ImperiyaSearchView : SearchView {

    private val searchBar: LinearLayout by viewProvider(androidx.appcompat.R.id.search_bar)
    private val searchIcon: ImageView by viewProvider(androidx.appcompat.R.id.search_mag_icon)
    private val searchText: EditText by viewProvider(androidx.appcompat.R.id.search_src_text)

    private val listener = object : OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }

    }

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        searchBar.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        searchBar.setBackgroundResource(R.drawable.imperiya_background_search)
        setIconifiedByDefault(false)
        searchText.setTextColor(ContextCompat.getColor(context, R.color.imperiya_title))
        searchText.setHintTextColor(ContextCompat.getColor(context, R.color.imperiya_hint))
    }

    fun onCLickListener(onClick: OnClickListener) {
        searchText.isFocusable = false
        searchText.setOnClickListener(onClick)
        searchIcon.setOnClickListener(onClick)
        setOnClickListener(onClick)
    }

    fun searchIcon(onClick: OnClickListener) {
        searchIcon.setOnClickListener(onClick)
    }

}