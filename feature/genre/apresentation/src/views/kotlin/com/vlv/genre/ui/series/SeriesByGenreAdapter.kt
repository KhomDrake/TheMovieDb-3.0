package com.vlv.genre.ui.series

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.data.network.model.genre.GenreResponse

class SeriesByGenreAdapter(
    private val genres: List<GenreResponse>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return SeriesByGenreFragment.instance(genres[position].id)
    }

    override fun getItemCount() = genres.size

}
