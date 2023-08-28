package com.vlv.movie.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.about.AboutFragment
import com.vlv.movie.ui.detail.cast.CastFragment
import com.vlv.movie.ui.detail.review.ReviewFragment

class DetailAdapter(
    private val titles: List<String>,
    private val movie: Movie,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
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
