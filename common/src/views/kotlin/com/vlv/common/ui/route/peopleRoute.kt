package com.vlv.common.ui.route

import android.content.Context
import com.vlv.common.data.people.People

fun Context.toPeoplePopular() = intentForAction("PEOPLE_POPULAR")

fun Context.toPeopleTrending() = intentForAction("PEOPLE_TRENDING")

const val EXTRA_PEOPLE = "EXTRA_PEOPLE"

fun Context.toPeopleDetail(
    people: People
) = intentForAction("PEOPLE_DETAIL").apply {
    putExtra(EXTRA_PEOPLE, people)
}
