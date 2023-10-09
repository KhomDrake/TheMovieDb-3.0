package com.vlv.themoviedb.ui.series

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.ui.route.toSeriesSearch
import com.vlv.extensions.addOrReplace
import com.vlv.imperiya.ui.search.ImperiyaSearchView
import com.vlv.movie.ui.search.SearchMovieActivity
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.series.airingtoday.AiringTodayFragment
import com.vlv.themoviedb.ui.series.favorites.SeriesFavoritesFragment
import com.vlv.themoviedb.ui.series.trending.TrendingFragment

class SeriesFragment : Fragment(R.layout.series_fragment) {

    private val search: ImperiyaSearchView by viewProvider(R.id.search)

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

        search.onCLickListener {
            startActivity(
                requireContext().toSeriesSearch(),
                ActivityOptions.makeSceneTransitionAnimation(
                    requireActivity(),
                    search,
                    getString(com.vlv.common.R.string.common_search_transition_name)
                ).toBundle()
            )
        }
    }

}