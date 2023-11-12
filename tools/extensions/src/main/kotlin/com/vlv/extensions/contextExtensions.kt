package com.vlv.extensions

import android.content.Context
import android.content.Intent
import android.util.TypedValue
import androidx.core.content.ContextCompat

fun Context.intentForAction(name: String) =
    Intent("$packageName.$name")

fun Context.getAttrColor(value: Int) : Int = run {
    val typedValue = TypedValue();
    theme.resolveAttribute(value, typedValue, true);
    ContextCompat.getColor(this, typedValue.resourceId)
}