package com.vlv.series.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vlv.series.data.Series
import com.vlv.series.ui.detail.about.AboutFragment
import com.vlv.series.ui.detail.cast.CastFragment
import com.vlv.series.ui.detail.review.ReviewFragment
import com.vlv.series.ui.detail.season.SeasonsFragment

class DetailAdapter(
    private val titles: List<String>,
    private val series: Series,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount() = 4

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                AboutFragment.instance(series)
            }
            1 -> {
                SeasonsFragment.instance(series)
            }
            2 -> {
                CastFragment.instance(series)
            }
            else -> {
                ReviewFragment.instance(series)
            }
        }
    }
}