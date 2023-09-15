package com.vlv.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.LayoutRes
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun ViewGroup.inflate(
    @LayoutRes
    layout: Int,
    attachToRoot: Boolean = false
) : View {
    return LayoutInflater.from(context).inflate(
        layout,
        this,
        attachToRoot
    )
}

fun View.setMargins(
    left: Int = marginLeft,
    top: Int = marginTop,
    right: Int = marginRight,
    bottom: Int = marginBottom
) {
    (layoutParams as? MarginLayoutParams)?.setMargins(
        left, top, right, bottom
    )
}
