package com.vlv.extensions

import android.view.View
import android.widget.Button
import androidx.annotation.StringRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import kotlin.reflect.KClass

fun View.addButtonAccessibilityDelegate() {
    setAccessibilityDelegate(Button::class)
}

fun View.addAccessibilityDelegate(
    @StringRes
    actionDescription: Int,
    @StringRes
    roleDescription: Int,
    action: Int = AccessibilityNodeInfoCompat.ACTION_CLICK
) {
    addAccessibilityDelegate(
        context.getString(actionDescription),
        context.getString(roleDescription),
        action
    )
}

fun View.addAccessibilityDelegate(
    @StringRes
    actionDescription: Int,
    action: Int = AccessibilityNodeInfoCompat.ACTION_CLICK
) {
    addAccessibilityDelegate(
        context.getString(actionDescription),
        null,
        action
    )
}

fun View.addAccessibilityDelegate(
    actionDescription: String,
    roleDescription: String?,
    action: Int = AccessibilityNodeInfoCompat.ACTION_CLICK
) {
    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                roleDescription?.let { info.roleDescription = it }
                val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    action, actionDescription
                )
                info.addAction(customAction)
            }
        }
    )
}

fun View.addHeadingAccessibilityDelegate() {
    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.isHeading = true
            }
        }
    )
}



fun View.setAccessibilityDelegate(className: KClass<*>) {
    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.className = className.java.name
            }
        }
    )
}