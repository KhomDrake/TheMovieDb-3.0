package com.vlv.themoviedb.ui.tv_show

import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.route.toTvShowsSearch
import com.vlv.extensions.addOrReplace
import com.vlv.imperiya.core.ui.search.ImperiyaSearchView
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.tv_show.airingtoday.AiringTodayFragment
import com.vlv.themoviedb.ui.tv_show.favorites.SeriesFavoritesFragment
import com.vlv.themoviedb.ui.tv_show.trending.TrendingFragment

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
                requireContext().toTvShowsSearch(),
                ActivityOptions.makeSceneTransitionAnimation(
                    requireActivity(),
                    search,
                    getString(com.vlv.ui.R.string.common_search_transition_name)
                ).toBundle()
            )
        }
    }

}