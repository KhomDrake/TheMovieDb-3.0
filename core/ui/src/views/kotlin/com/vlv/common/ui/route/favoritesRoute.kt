package com.vlv.common.ui.route

import android.content.Context
import com.vlv.network.database.data.FavoriteType

const val FAVORITE_TYPE_EXTRA = "FAVORITE_TYPE_EXTRA"

fun Context.toFavorites(
    favoriteType: FavoriteType
) = intentForAction("FAVORITES_LISTING")
    .apply {
        putExtra(FAVORITE_TYPE_EXTRA, favoriteType)
    }