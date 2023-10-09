package com.vlv.themoviedb.ui.movie

import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.ui.route.toMovieSearch
import com.vlv.extensions.addOrReplace
import com.vlv.imperiya.ui.search.ImperiyaSearchView
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.favorites.MovieFavoritesFragment
import com.vlv.themoviedb.ui.movie.nowplaying.NowPlayingFragment
import com.vlv.themoviedb.ui.movie.trending.TrendingNowFragment

class MovieFragment : Fragment(R.layout.movie_fragment) {

    private val search: ImperiyaSearchView by viewProvider(R.id.search)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().apply {
            addOrReplace(
                childFragmentManager,
                R.id.content,
                TrendingNowFragment(),
                TrendingNowFragment::class.java.name
            )
            addOrReplace(
                childFragmentManager,
                R.id.content,
                NowPlayingFragment(),
                NowPlayingFragment::class.java.name
            )
            addOrReplace(
                childFragmentManager,
                R.id.content,
                MovieFavoritesFragment(),
                MovieFavoritesFragment::class.java.name
            )
        }.commit()
        search.onCLickListener {
            startActivity(
                requireContext().toMovieSearch(),
                ActivityOptions.makeSceneTransitionAnimation(
                    requireActivity(),
                    search,
                    getString(com.vlv.common.R.string.common_search_transition_name)
                ).toBundle()
            )
        }
    }

}