package com.vlv.genre.ui.movie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.network.data.genre.GenreResponse

class MovieByGenreAdapter(
    private val genres: List<GenreResponse>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return MovieByGenreFragment.instance(genres[position].id)
    }

    override fun getItemCount() = genres.size

}
