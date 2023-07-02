package com.vlv.themoviedb.ui.movie

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.search.ImperiyaSearchView
import com.vlv.movie.ui.search.SearchMovieActivity
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
            val intent = Intent(
                requireContext(),
                SearchMovieActivity::class.java
            )

            startActivity(
                intent,
                ActivityOptions.makeSceneTransitionAnimation(
                    requireActivity(),
                    android.util.Pair(search, "abcd")
                ).toBundle()
            )
        }
    }

}

fun FragmentTransaction.addOrReplace(
    fragmentManager: FragmentManager,
    @IdRes layout: Int,
    fragment: Fragment,
    tag: String
) {
    fragmentManager.findFragmentByTag(tag)?.let {
        remove(it)
    }
    add(layout, fragment, tag)
}