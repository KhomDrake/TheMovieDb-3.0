package com.vlv.common.route

import android.content.Context
import com.vlv.data.network.database.data.FavoriteType
import com.vlv.extensions.intentForAction

const val FAVORITE_TYPE_EXTRA = "FAVORITE_TYPE_EXTRA"

fun Context.toFavorites(
    favoriteType: FavoriteType
) = intentForAction("FAVORITES_LISTING")
    .apply {
        putExtra(FAVORITE_TYPE_EXTRA, favoriteType)
    }