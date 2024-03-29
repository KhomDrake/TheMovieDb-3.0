package com.vlv.common.route

import android.content.Context
import com.vlv.data.database.data.ItemType
import com.vlv.extensions.intentForAction

const val FAVORITE_TYPE_EXTRA = "FAVORITE_TYPE_EXTRA"

fun Context.toFavorites(
    favoriteType: ItemType
) = intentForAction("FAVORITES_LISTING")
    .apply {
        putExtra(FAVORITE_TYPE_EXTRA, favoriteType)
    }