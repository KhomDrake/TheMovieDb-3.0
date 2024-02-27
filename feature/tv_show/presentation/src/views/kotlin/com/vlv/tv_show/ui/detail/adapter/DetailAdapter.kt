package com.vlv.tv_show.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.common.data.tv_show.TvShow
import com.vlv.tv_show.ui.detail.about.AboutFragment
import com.vlv.tv_show.ui.detail.cast.TvShowCastFragment
import com.vlv.tv_show.ui.detail.recommendation.RecommendationFragment
import com.vlv.tv_show.ui.detail.review.TvShowReviewFragment
import com.vlv.tv_show.ui.detail.season.SeasonsFragment

class DetailAdapter(
    private val titles: List<String>,
    private val tvShow: TvShow,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                AboutFragment.instance(tvShow)
            }
            1 -> {
                SeasonsFragment.instance(tvShow)
            }
            2 -> {
                TvShowCastFragment.instance(tvShow)
            }
            3 -> {
                TvShowReviewFragment.instance(tvShow)
            }
            else -> {
                RecommendationFragment.instance(tvShow)
            }
        }
    }
}