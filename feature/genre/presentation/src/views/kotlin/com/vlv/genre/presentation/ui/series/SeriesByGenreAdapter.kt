package com.vlv.genre.presentation.ui.series

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.genre.presentation.data.Genre

class SeriesByGenreAdapter(
    private val genres: List<Genre>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return SeriesByGenreFragment.instance(genres[position].id)
    }

    override fun getItemCount() = genres.size

}
