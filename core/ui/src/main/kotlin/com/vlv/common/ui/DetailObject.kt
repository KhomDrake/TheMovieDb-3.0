package com.vlv.common.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DetailObject(
    val id: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val title: String,
    val overview: String,
    val adult: Boolean = false
) : Parcelable