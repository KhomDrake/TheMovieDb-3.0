package com.vlv.extensions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import androidx.core.content.ContextCompat
import java.io.Serializable

fun Context.intentForAction(name: String) =
    Intent("$packageName.$name")

fun Context.getAttrColor(value: Int) : Int = run {
    val typedValue = TypedValue();
    theme.resolveAttribute(value, typedValue, true);
    ContextCompat.getColor(this, typedValue.resourceId)
}
fun Context.getAttrColorResourceId(value: Int) : Int = run {
    val typedValue = TypedValue();
    theme.resolveAttribute(value, typedValue, true);
    typedValue.resourceId
}

inline fun <reified T : java.io.Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
