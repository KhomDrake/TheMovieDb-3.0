package com.vlv.tv_show.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.common.data.tv_show.TvShow
import com.vlv.tv_show.ui.detail.cast.SeriesCastFragment
import com.vlv.tv_show.ui.detail.recommendation.RecommendationFragment
import com.vlv.tv_show.ui.detail.review.SeriesReviewFragment
import com.vlv.tv_show.ui.detail.season.SeasonsFragment
import com.vlv.tv_show.ui.detail.about.AboutFragment

class DetailAdapter(
    private val titles: List<String>,
    private val series: TvShow,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = titles.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                AboutFragment.instance(series)
            }
            1 -> {
                SeasonsFragment.instance(series)
            }
            2 -> {
                SeriesCastFragment.instance(series)
            }
            3 -> {
                SeriesReviewFragment.instance(series)
            }
            else -> {
                RecommendationFragment.instance(series)
            }
        }
    }
}