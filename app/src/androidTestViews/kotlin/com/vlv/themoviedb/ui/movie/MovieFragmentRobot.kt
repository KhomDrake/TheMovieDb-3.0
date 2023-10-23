package com.vlv.themoviedb.ui.movie

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun MovieFragmentTest.movieFragment(func: MovieFragmentSetup.() -> Unit) =
    MovieFragmentSetup().apply(func)

class MovieFragmentSetup : Setup<MovieFragmentLaunch, MovieFragmentCheck> {
    override fun createCheck(): MovieFragmentCheck {
        return MovieFragmentCheck()
    }

    override fun createLaunch(): MovieFragmentLaunch {
        return MovieFragmentLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withFavorites() {
        
    }

    fun withMovieTrending() {
        
    }

    fun withMoviePlayingNow() {
        
    }

}

class MovieFragmentLaunch : Launch<MovieFragmentCheck> {
    override fun createCheck(): MovieFragmentCheck {
        return MovieFragmentCheck()
    }
}

class MovieFragmentCheck : Check {
    fun favoritesLoaded() {
        
    }

    fun nowPlayingLoaded() {
        
    }

    fun trendingLoaded() {
        
    }

}