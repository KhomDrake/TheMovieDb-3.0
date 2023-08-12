package com.vlv.extensions

import android.content.Context
import android.content.Intent

fun Context.intentForAction(name: String) =
    Intent("$packageName.$name")

fun Context.toDetail() = intentForAction("MOVIE_DETAIL")
