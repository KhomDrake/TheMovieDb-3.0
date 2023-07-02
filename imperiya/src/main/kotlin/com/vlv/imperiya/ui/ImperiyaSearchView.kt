package com.vlv.imperiya.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.google.android.material.textview.MaterialTextView
import com.vlv.imperiya.R

class ImperiyaSearchView : SearchView {

    private val badge: MaterialTextView by viewProvider(androidx.appcompat.R.id.search_badge)
    private val button: AppCompatImageView by viewProvider(androidx.appcompat.R.id.search_button)
    private val editFrame: LinearLayout by viewProvider(androidx.appcompat.R.id.search_edit_frame)
    private val plate: LinearLayout by viewProvider(androidx.appcompat.R.id.search_plate)
    private val submitArea: LinearLayout by viewProvider(androidx.appcompat.R.id.submit_area)
    private val searchBar: LinearLayout by viewProvider(androidx.appcompat.R.id.search_bar)
    private val searchIcon: ImageView by viewProvider(androidx.appcompat.R.id.search_mag_icon)
    private val searchCloseButton: ImageView by viewProvider(androidx.appcompat.R.id.search_close_btn)
    private val searchText: EditText by viewProvider(androidx.appcompat.R.id.search_src_text)
    private val searchText2: SearchView.SearchAutoComplete by viewProvider(androidx.appcompat.R.id.search_src_text)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        searchBar.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        searchBar.setBackgroundResource(R.drawable.imperiya_background_search)
        setIconifiedByDefault(false)
        searchCloseButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_search))
        searchText.setTextColor(ContextCompat.getColor(context, R.color.imperiya_title))
        searchText.setHintTextColor(ContextCompat.getColor(context, R.color.imperiya_hint))
    }

    fun setHint(@StringRes text: Int) = apply {
        searchText.hint = context.getString(text)
    }

    fun setHint(text: String) = apply {
        searchText.hint = text
    }

    fun onCLickListener(onClick: OnClickListener) {
        searchText.isFocusable = false
        searchText.setOnClickListener(onClick)
        searchIcon.setOnClickListener(onClick)
        setOnClickListener(onClick)
    }

    fun onQuery(
        onTextChanged: (String?) -> Unit = {},
        onTextSubmit: (String?) -> Unit = {}
    ) {
        setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onTextSubmit.invoke(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onTextChanged.invoke(newText)
                return true
            }

        })
    }

    fun searchIcon(onClick: OnClickListener) {
        searchIcon.setOnClickListener(onClick)
    }

}