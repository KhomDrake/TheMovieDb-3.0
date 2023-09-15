package com.vlv.themoviedb.ui.menu

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.themoviedb.R
import com.vlv.imperiya.R as RCommon

class MenuViewModel : ViewModel() {

    fun menuItems() = bondsmith<List<MenuItem>>()
        .request {
            listOf(
                MenuItem(
                    R.string.trending_title,
                    type = MenuItemType.HEADER
                ),
                MenuItem(
                    R.string.favorites_title,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_tv_off
                ),
                MenuItem(
                    R.string.now_playing_title,
                    type = MenuItemType.HEADER
                ),
                MenuItem(
                    R.string.airing_today_title,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                )
            )
        }
        .execute()
        .responseLiveData

}