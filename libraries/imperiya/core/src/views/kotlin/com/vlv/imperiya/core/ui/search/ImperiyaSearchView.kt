package com.vlv.imperiya.core.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.extensions.getAttrColor
import com.vlv.imperiya.core.R

class ImperiyaSearchView : SearchView {

    private val searchBar: LinearLayout by viewProvider(androidx.appcompat.R.id.search_bar)
    private val startIcon: ImageView by viewProvider(androidx.appcompat.R.id.search_mag_icon)
    private val endIcon: ImageView by viewProvider(androidx.appcompat.R.id.search_close_btn)
    private val searchText: EditText by viewProvider(androidx.appcompat.R.id.search_src_text)
    private val searchPlate: View by viewProvider(androidx.appcompat.R.id.search_plate)

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        searchBar.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        searchBar.setBackgroundResource(R.drawable.imperiya_background_search)
        setIconifiedByDefault(false)
        searchText.setTextColor(ContextCompat.getColor(context, R.color.color_imperiya_on_tertiary))
        searchText.setHintTextColor(ContextCompat.getColor(context, R.color.color_imperiya_hint_tertiary))
        searchPlate.setBackgroundResource(android.R.color.transparent)
        startIcon.foreground = ContextCompat.getDrawable(context, R.drawable.imperiya_ripple_oval)
        endIcon.foreground = ContextCompat.getDrawable(context, R.drawable.imperiya_ripple_oval)
    }

    fun setTextColor(color: Int) = apply {
        searchText.setTextColor(color)
    }

    fun setSearchIcon(@DrawableRes drawableRes: Int) = apply {
        startIcon.setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
    }

    fun setCloseIcon(@DrawableRes drawableRes: Int) = apply {
        endIcon.setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
    }

    fun setHint(@StringRes text: Int) = apply {
        searchText.hint = context.getString(text)
    }

    fun setHint(text: String) = apply {
        searchText.hint = text
    }

    fun setText(text: String) = apply {
        searchText.setText(text)
    }

    fun onCLickListener(onClick: OnClickListener) {
        searchText.isFocusable = false
        searchText.setOnClickListener(onClick)
        startIcon.setOnClickListener(onClick)
        setOnClickListener(onClick)
    }

    fun onClickSearchListener(onClick: OnClickListener) {
        startIcon.setOnClickListener(onClick)
    }

    fun onClickCloseListener(onClick: OnClickListener) {
        endIcon.setOnClickListener(onClick)
    }

    fun setup(
        onTextChanged: (String?) -> Unit = {},
        onTextSubmit: (String?) -> Unit = {}
    ) {
        setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onTextSubmit.invoke(query?.trim())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onTextChanged.invoke(newText?.trim())
                return true
            }

        })
    }

    fun searchIcon(onClick: OnClickListener) {
        startIcon.setOnClickListener(onClick)
    }

    fun clearText() {
        setText("")
    }

}