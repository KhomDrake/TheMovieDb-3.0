package com.vlv.common.route

import android.content.Context
import com.vlv.extensions.intentForAction

fun Context.toMain() = intentForAction("MAIN")