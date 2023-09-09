package com.vlv.imperiya.ui.warning

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.R

class WarningView : ConstraintLayout {

    private val title: AppCompatTextView by viewProvider(R.id.title)
    private val body: AppCompatTextView by viewProvider(R.id.body)
    private val tryAgainButton: AppCompatButton by viewProvider(R.id.try_again_button)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.imperiya_widget_warning_view, this)
        setupWithAttributes(context, attrs)
    }

    fun setOnTryAgain(onClickListener: OnClickListener) {
        tryAgainButton.setOnClickListener(onClickListener)
    }

    private fun setupWithAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.WarningView)

        if(attrs.hasValue(R.styleable.WarningView_warning_title)) {
            val titleText = attrs.getString(R.styleable.WarningView_warning_title)
            title.text = titleText
        }

        if(attrs.hasValue(R.styleable.WarningView_warning_body)) {
            val bodyText = attrs.getString(R.styleable.WarningView_warning_body)
            body.text = bodyText
        }

        if(attrs.hasValue(R.styleable.WarningView_warning_button_try_again)) {
            val tryAgainText = attrs.getString(R.styleable.WarningView_warning_button_try_again)
            tryAgainButton.text = tryAgainText
        }

        attrs.recycle()
    }

}