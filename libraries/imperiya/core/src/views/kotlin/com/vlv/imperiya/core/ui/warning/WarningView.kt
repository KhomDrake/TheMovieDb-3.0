package com.vlv.imperiya.core.ui.warning

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.core.R

class WarningView : ConstraintLayout {

    private val title: AppCompatTextView by viewProvider(R.id.small_warning_title)
    private val body: AppCompatTextView by viewProvider(R.id.small_warning_body)
    private val statusIcon: AppCompatImageView by viewProvider(R.id.status_icon)
    private val tryAgainButton: AppCompatTextView by viewProvider(R.id.small_warning_try_again_button)
    private val closeIcon: AppCompatImageView by viewProvider(R.id.close_icon)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.imperiya_widget_warning_view, this)
        setupWithAttributes(context, attrs)
    }

    private fun setupWithAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.WarningView)

        setTitle(attrs.getString(R.styleable.WarningView_warning_title))
        setBody(attrs.getString(R.styleable.WarningView_warning_body))
        setButtonText(attrs.getString(R.styleable.WarningView_warning_button_try_again))
        setStatusIcon(attrs.getResourceId(R.styleable.WarningView_warning_status_icon, R.drawable.ic_close_error))
        setVisibilityIcon(attrs.getBoolean(R.styleable.WarningView_warning_show_close_icon, true))

        attrs.recycle()
    }

    fun setOnTryAgain(onClickListener: OnClickListener) {
        tryAgainButton.setOnClickListener(onClickListener)
    }

    fun setCloseIcon(onClickListener: OnClickListener) {
        closeIcon.setOnClickListener(onClickListener)
    }

    fun setTitle(@StringRes text: Int) = apply {
        val newText = resources.getString(text)
        setTitle(newText)
    }

    fun setTitle(text: String?) = apply {
        title.text = text
    }

    fun setVisibilityIcon(show: Boolean) = apply {
        closeIcon.isVisible = show
    }

    fun setBody(@StringRes text: Int) = apply {
        val newText = resources.getString(text)
        setBody(newText)
    }

    fun setBody(text: String?) = apply {
        body.text = text
    }

    fun setButtonText(@StringRes text: Int) = apply {
        val newText = resources.getString(text)
        setButtonText(newText)
    }

    fun setButtonText(text: String?) = apply {
        tryAgainButton.text = text
        tryAgainButton.isInvisible = text.isNullOrBlank()
    }

    fun setStatusIcon(@DrawableRes drawableRes: Int) = apply {
        statusIcon.setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
    }


}