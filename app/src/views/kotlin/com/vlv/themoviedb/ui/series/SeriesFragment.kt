package com.vlv.themoviedb.ui.series

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vlv.extensions.addOrReplace
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.series.airingtoday.AiringTodayFragment
import com.vlv.themoviedb.ui.series.favorites.SeriesFavoritesFragment
import com.vlv.themoviedb.ui.series.trending.TrendingFragment

class SeriesFragment : Fragment(R.layout.series_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().apply {
            addOrReplace(
                childFragmentManager,
                R.id.content,
                TrendingFragment(),
                TrendingFragment::class.java.name
            )
            addOrReplace(
                childFragmentManager,
                R.id.content,
                AiringTodayFragment(),
                AiringTodayFragment::class.java.name
            )
            addOrReplace(
                childFragmentManager,
                R.id.content,
                SeriesFavoritesFragment(),
                SeriesFavoritesFragment::class.java.name
            )
        }.commit()
    }

}