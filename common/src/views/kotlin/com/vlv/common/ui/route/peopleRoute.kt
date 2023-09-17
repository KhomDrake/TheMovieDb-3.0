package com.vlv.common.ui.route

import android.content.Context

fun Context.toPeoplePopular() = intentForAction("PEOPLE_POPULAR")

fun Context.toPeopleTrending() = intentForAction("PEOPLE_TRENDING")
