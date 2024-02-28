package com.vlv.favorite.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.favorite.ui.movie.MovieFavoritesFragment
import com.vlv.favorite.ui.people.PeopleFavoritesFragment
import com.vlv.favorite.ui.tv_show.TvShowFavoritesFragment

class FavoritesAdapter(
    private val itemCount: Int,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MovieFavoritesFragment()
            1 -> TvShowFavoritesFragment()
            else -> PeopleFavoritesFragment()
        }
    }

    override fun getItemCount() = itemCount

}