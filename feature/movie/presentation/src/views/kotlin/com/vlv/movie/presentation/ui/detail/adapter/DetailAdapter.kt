package com.vlv.movie.presentation.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.common.data.movie.Movie
import com.vlv.movie.presentation.ui.detail.about.AboutFragment
import com.vlv.movie.presentation.ui.detail.cast.MovieCastFragment
import com.vlv.movie.presentation.ui.detail.recommendation.RecommendationFragment
import com.vlv.movie.presentation.ui.detail.review.MovieReviewFragment

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
            2 -> {
                MovieReviewFragment.instance(movie)
            }
            else -> {
                RecommendationFragment.instance(movie)
            }
        }
    }

    override fun getItemCount() = titles.size

}
