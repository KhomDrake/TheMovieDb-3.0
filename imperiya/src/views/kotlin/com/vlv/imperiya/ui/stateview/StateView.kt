package com.vlv.imperiya.ui.stateview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.R

class StateView : ConstraintLayout {

    private val title: AppCompatTextView by viewProvider(R.id.title_state)
    private val body: AppCompatTextView by viewProvider(R.id.body_state)
    private val icon: AppCompatImageView by viewProvider(R.id.icon_state)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.imperiya_widget_state_view, this)
        setupWithAttributes(context, attrs)
    }

    private fun setupWithAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.StateView)

        setTitle(attrs.getString(R.styleable.StateView_state_title))
        setBody(attrs.getString(R.styleable.StateView_state_body))
        setStateIcon(attrs.getResourceId(R.styleable.StateView_state_icon, R.drawable.ic_hearts))

        attrs.recycle()
    }

    fun setTitle(@StringRes text: Int) = apply {
        val newText = resources.getString(text)
        setTitle(newText)
    }

    fun setTitle(text: String?) = apply {
        title.text = text
    }

    fun setBody(@StringRes text: Int) = apply {
        val newText = resources.getString(text)
        setBody(newText)
    }

    fun setBody(text: String?) = apply {
        body.text = text
        body.isVisible = !text.isNullOrBlank()
    }

    fun setStateIcon(@DrawableRes drawableRes: Int) = apply {
        icon.setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
    }

}