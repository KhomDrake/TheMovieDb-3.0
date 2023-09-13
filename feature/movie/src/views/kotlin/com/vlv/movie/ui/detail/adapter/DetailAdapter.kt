package com.vlv.movie.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.about.AboutFragment
import com.vlv.movie.ui.detail.cast.MovieCastFragment
import com.vlv.movie.ui.detail.review.ReviewFragment

class DetailAdapter(
    private val titles: List<String>,
    private val movie: Movie,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                AboutFragment.instance(movie)
            }
            1 -> {
                MovieCastFragment.instance(movie)
            }
            else -> {
                ReviewFragment.instance(movie)
            }
        }
    }

    override fun getItemCount() = titles.size

}
