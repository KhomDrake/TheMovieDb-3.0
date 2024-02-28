package com.vlv.common.data.about

import androidx.annotation.StringRes
import kotlin.random.Random

enum class AboutItemType {
    LINE,
    TITLE,
    BIG_TEXT,
    INFORMATION,
    GENRES,
    EPISODE
}

class Information(
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE),
    @StringRes
    val title: Int,
    val data: String
)

class PillItem(
    val id: Int,
    val title: String
)

sealed class AboutItem(
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE),
    val type: AboutItemType
) {

    class Line: AboutItem(type = AboutItemType.LINE)

    class Title(@StringRes val text: Int) : AboutItem(type = AboutItemType.TITLE)

    class BigText(
        val description: String? = null,
        val text: String
    ) : AboutItem(type = AboutItemType.BIG_TEXT)

    class Episode(
        val title: String,
        val description: String,
        val poster: String? = null
    ) : AboutItem(type = AboutItemType.EPISODE)

    class InformationItem(
        val information: Information
    ) : AboutItem(type = AboutItemType.INFORMATION)

    class Genres(
        val pillItems: List<PillItem>
    ) : AboutItem(type = AboutItemType.GENRES)

}