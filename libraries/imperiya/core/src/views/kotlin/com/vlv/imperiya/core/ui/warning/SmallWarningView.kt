package com.vlv.imperiya.core.ui.warning

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.core.R


class SmallWarningView : LinearLayoutCompat {

    private val title: AppCompatTextView by viewProvider(R.id.small_warning_title)
    private val body: AppCompatTextView by viewProvider(R.id.small_warning_body)
    private val tryAgainButton: AppCompatTextView by viewProvider(R.id.small_warning_try_again_button)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.imperiya_widget_small_warning_view, this)
        orientation = VERTICAL
        setBackgroundResource(R.drawable.imperiya_background_tertiary)

        val paddingVertical = resources.getDimension(R.dimen.imperiya_carousel_m).toInt()
        setPadding(paddingLeft, paddingVertical, paddingRight, paddingVertical)
        setupWithAttributes(context, attrs)
    }

    fun setOnClickLink(onClickListener: OnClickListener) {
        tryAgainButton.setOnClickListener(onClickListener)
    }

    private fun setupWithAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.SmallWarningView)

        setTitleText(attrs.getString(R.styleable.SmallWarningView_small_warning_title))
        setBodyText(attrs.getString(R.styleable.SmallWarningView_small_warning_body))
        setButtonText(attrs.getString(R.styleable.SmallWarningView_small_warning_button_try_again))

        if(attrs.hasValue(R.styleable.SmallWarningView_small_warning_with_icon)) {
            val withIcon = attrs.getBoolean(
                R.styleable.SmallWarningView_small_warning_with_icon,
                true
            )
            if(!withIcon) title.setCompoundDrawables(null, null, null, null)
        }

        attrs.recycle()
    }

    fun setTitleText(@StringRes text: Int) {
        setTitleText(resources.getString(text))
    }

    fun setTitleText(text: String?) {
        title.text = text
        title.isVisible = text.isNullOrBlank().not()
    }

    fun setBodyText(@StringRes text: Int) {
        setBodyText(resources.getString(text))
    }

    fun setBodyText(text: String?) {
        body.text = text
        body.isVisible = text.isNullOrBlank().not()
    }

    fun setButtonText(@StringRes text: Int) {
        setButtonText(resources.getString(text))
    }

    fun setButtonText(text: String?) {
        tryAgainButton.text = text
        tryAgainButton.isVisible = text.isNullOrBlank().not()
    }

}