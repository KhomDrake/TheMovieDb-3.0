package com.vlv.common.ui.route

import android.content.Context
import com.vlv.common.data.people.People
import com.vlv.common.ui.FINISH_AFTER_TRANSITION_EXTRA

fun Context.toPeoplePopular() = intentForAction("PEOPLE_POPULAR")

fun Context.toPeopleTrending() = intentForAction("PEOPLE_TRENDING")

const val EXTRA_PEOPLE = "EXTRA_PEOPLE"

fun Context.toPeopleSearch() = intentForAction("PEOPLE_SEARCH")

fun Context.toPeopleDetail(
    people: People,
    finishAfterTransition: Boolean = true
) = intentForAction("PEOPLE_DETAIL").apply {
    putExtra(EXTRA_PEOPLE, people)
    putExtra(FINISH_AFTER_TRANSITION_EXTRA, finishAfterTransition)
}
