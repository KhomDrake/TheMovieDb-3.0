package com.vlv.series.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.common.data.series.Series
import com.vlv.series.ui.detail.about.AboutFragment
import com.vlv.series.ui.detail.cast.SeriesCastFragment
import com.vlv.series.ui.detail.recommendation.RecommendationFragment
import com.vlv.series.ui.detail.review.SeriesReviewFragment
import com.vlv.series.ui.detail.season.SeasonsFragment

class DetailAdapter(
    private val titles: List<String>,
    private val series: Series,
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