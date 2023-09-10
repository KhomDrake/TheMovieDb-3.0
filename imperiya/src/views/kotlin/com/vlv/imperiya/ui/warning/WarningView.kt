package com.vlv.imperiya.ui.warning

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
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

        setTitle(attrs.getString(R.styleable.WarningView_warning_title))
        setBody(attrs.getString(R.styleable.WarningView_warning_body))
        setButtonText(attrs.getString(R.styleable.WarningView_warning_button_try_again))

        attrs.recycle()
    }

    fun setTitle(@StringRes text: Int) {
        val newText = resources.getString(text)
        setTitle(newText)
    }

    fun setTitle(text: String?) {
        title.text = text
    }

    fun setBody(@StringRes text: Int) {
        val newText = resources.getString(text)
        setBody(newText)
    }

    fun setBody(text: String?) {
        body.text = text
    }

    fun setButtonText(@StringRes text: Int) {
        val newText = resources.getString(text)
        setButtonText(newText)
    }

    fun setButtonText(text: String?) {
        tryAgainButton.text = text
    }



}