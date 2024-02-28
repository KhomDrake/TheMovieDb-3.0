package com.vlv.extensions

import android.content.res.Resources

const val MINUTES_IN_HOUR = 60
const val SECONDS_IN_MINUTES = 60
const val BILLION = 1_000_000_000
const val MILLION = 1_000_000
const val THOUSAND = 1_000

fun Int.toHours(resources: Resources) = run {
    val hours = this / MINUTES_IN_HOUR
    resources.getString(R.string.extensions_hours, hours)
}

fun Int.toHoursAndMinutes(resources: Resources) = run {
    val hours = this / MINUTES_IN_HOUR
    if(hours == 0)
        toMinutes(resources)
    else {
        val minutes = this % MINUTES_IN_HOUR
        resources.getString(R.string.extensions_hours_and_minutes, hours, minutes)
    }
}

fun Int.toMinutes(resources: Resources) = run {
    resources.getString(R.string.extensions_minutes, this)
}

fun Int.toMillions(resources: Resources) = run {
    val millions = this / MILLION
    resources.getQuantityString(R.plurals.extensions_million, millions)
}

fun Double.toMillions(resources: Resources) = run {
    val millions = this / MILLION
    resources.getQuantityString(R.plurals.extensions_million, millions.toInt())
}

fun List<Int>.toHoursAndMinutes(resources: Resources) = run {
    if(isEmpty()) return@run "Not available"

    val times = this.map { it.toHoursAndMinutes(resources) }
    times.joinToString { it }
}

fun Int.toMillionsAndThousands(resources: Resources) = run {
    val millions = this / MILLION
    val thousands = (this % MILLION) / THOUSAND
    if(thousands == 0)
        resources.getQuantityString(R.plurals.extensions_million, millions, millions)
    else {
        val millionsText = resources.getQuantityString(
            R.plurals.extensions_million, millions, millions
        )
        val thousandsText = resources.getQuantityString(
            R.plurals.extensions_thousand, thousands, thousands
        )
        resources.getString(
            R.string.extensions_millions_and_thousands,
            millionsText,
            thousandsText
        )
    }
}

fun Double.toMillionsAndThousands(resources: Resources) = run {
    val millions = (this / MILLION).toInt()
    val thousands = ((this % MILLION) / THOUSAND).toInt()
    if(thousands == 0)
        resources.getQuantityString(R.plurals.extensions_million, millions, millions)
    else {
        val millionsText = resources.getQuantityString(
            R.plurals.extensions_million, millions, millions
        )
        val thousandsText = resources.getQuantityString(
            R.plurals.extensions_thousand, thousands, thousands
        )
        resources.getString(
            R.string.extensions_millions_and_thousands,
            millionsText,
            thousandsText
        )
    }
}

fun Int.toBillionsAndMillions(resources: Resources) = run {
    val billions = this / BILLION
    val millions = (this % BILLION) / MILLION

    when (billions) {
        0 -> toMillionsAndThousands(resources)
        else -> {
            val billionsText = resources.getQuantityString(
                R.plurals.extensions_billion, billions, billions
            )
            val millionsText = resources.getQuantityString(
                R.plurals.extensions_million, millions, millions
            )
            resources.getString(
                R.string.extensions_billions_and_millions,
                billionsText,
                millionsText
            )
        }
    }

}

fun Double.toBillionsAndMillions(resources: Resources) = run {
    val billions = (this / BILLION).toInt()
    val millions = ((this % BILLION) / MILLION).toInt()

    when (billions) {
        0 -> toMillionsAndThousands(resources)
        else -> {
            val billionsText = resources.getQuantityString(
                R.plurals.extensions_billion, billions, billions
            )
            val millionsText = resources.getQuantityString(
                R.plurals.extensions_million, millions, millions
            )
            resources.getString(
                R.string.extensions_billions_and_millions,
                billionsText,
                millionsText
            )
        }
    }

}

var previousId = Int.MIN_VALUE

fun idInt(): Int {
    return previousId++
}
