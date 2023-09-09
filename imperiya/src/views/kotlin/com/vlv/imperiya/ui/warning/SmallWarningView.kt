package com.vlv.imperiya.ui.warning

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.R


class SmallWarningView : LinearLayoutCompat {

    private val title: AppCompatTextView by viewProvider(R.id.title)
    private val body: AppCompatTextView by viewProvider(R.id.body)
    private val tryAgainButton: AppCompatTextView by viewProvider(R.id.try_again_button)

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

    fun setOnTryAgain(onClickListener: OnClickListener) {
        tryAgainButton.setOnClickListener(onClickListener)
    }

    private fun setupWithAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.SmallWarningView)

        setTitleText(attrs.getString(R.styleable.SmallWarningView_small_warning_title))
        setBodyText(attrs.getString(R.styleable.SmallWarningView_small_warning_body))

        if(attrs.hasValue(R.styleable.SmallWarningView_small_warning_button_try_again)) {
            val tryAgainText = attrs.
            getString(R.styleable.SmallWarningView_small_warning_button_try_again)
            tryAgainButton.text = tryAgainText
        }

        if(attrs.hasValue(R.styleable.SmallWarningView_small_warning_with_icon)) {
            val withIcon = attrs.getBoolean(
                R.styleable.SmallWarningView_small_warning_with_icon,
                true
            )
            if(!withIcon) title.setCompoundDrawables(null, null, null, null)
        }

        attrs.recycle()
    }

    fun setTitleText(text: String?) {
        title.text = text
        title.isVisible = text.isNullOrBlank().not()
    }

    fun setBodyText(text: String?) {
        body.text = text
        body.isVisible = text.isNullOrBlank().not()
    }

}