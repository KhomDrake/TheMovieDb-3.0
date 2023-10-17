package com.vlv.movie.ui.search

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun SearchMovieActivityTest.movieDetailActivity(func: SearchMovieActivitySetup.() -> Unit) =
    SearchMovieActivitySetup().apply(func)

class SearchMovieActivitySetup : Setup<SearchMovieActivityLaunch, SearchMovieActivityCheck> {
    override fun createCheck(): SearchMovieActivityCheck {
        return SearchMovieActivityCheck()
    }

    override fun createLaunch(): SearchMovieActivityLaunch {
        return SearchMovieActivityLaunch()
    }

    override fun setupLaunch() {

    }
}

class SearchMovieActivityLaunch : Launch<SearchMovieActivityCheck> {
    override fun createCheck(): SearchMovieActivityCheck {
        return SearchMovieActivityCheck()
    }

}

class SearchMovieActivityCheck : Check {

}
