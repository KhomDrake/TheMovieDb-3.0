package com.vlv.movie.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.about.AboutFragment
import com.vlv.movie.ui.detail.cast.CastFragment
import com.vlv.movie.ui.detail.review.ReviewFragment

class DetailAdapter(
    private val movie: Movie,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                AboutFragment.instance(movie)
            }
            1 -> {
                CastFragment.instance(movie)
            }
            else -> {
                ReviewFragment.instance(movie)
            }
        }
    }
}