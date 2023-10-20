package com.vlv.extensions


import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.addOrReplace(
    @IdRes layout: Int,
    fragment: Fragment,
    tag: String
) = apply {
    beginTransaction()
        .addOrReplace(this@addOrReplace, layout, fragment, tag)
        .commit()
}

fun FragmentManager.addOrReplace(
    fragments: List<Pair<Int, Fragment>>,
    tag: String
) = apply {
    beginTransaction()
        .apply {
            fragments.forEach {
                val layout = it.first
                val fragment = it.second
                addOrReplace(this@addOrReplace, layout, fragment, tag)
            }
        }
        .commit()
}

fun FragmentTransaction.addOrReplace(
    fragmentManager: FragmentManager,
    @IdRes layout: Int,
    fragment: Fragment,
    tag: String
) = apply {
    fragmentManager.findFragmentByTag(tag)?.let {
        remove(it)
    }
    add(layout, fragment, tag)
}