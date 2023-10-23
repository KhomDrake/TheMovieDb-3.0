package com.vlv.themoviedb.ui.movie.favorites

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun MovieFavoritesFragmentTest.movieFavoritesFragment(func: MovieFavoritesFragmentSetup.() -> Unit) =
    MovieFavoritesFragmentSetup().apply(func)

class MovieFavoritesFragmentSetup : Setup<MovieFavoritesFragmentLaunch, MovieFavoritesFragmentCheck> {
    override fun createCheck(): MovieFavoritesFragmentCheck {
        return MovieFavoritesFragmentCheck()
    }

    override fun createLaunch(): MovieFavoritesFragmentLaunch {
        return MovieFavoritesFragmentLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withFavoritesSuccess() {
        
    }

    fun withFavoritesError() {
        
    }

    fun withFavoritesEmpty() {
        
    }

}

class MovieFavoritesFragmentLaunch : Launch<MovieFavoritesFragmentCheck> {
    override fun createCheck(): MovieFavoritesFragmentCheck {
        return MovieFavoritesFragmentCheck()
    }

    fun clickTryAgain() {
        
    }

    fun clickFavorite(position: Int) {

    }

    fun clickSeeAll() {
        
    }
}

class MovieFavoritesFragmentCheck : Check {
    fun favoritesDisplayed() {
        
    }

    fun emptyStateDisplayed() {
        
    }

    fun errorStateDisplayed() {
        
    }

    fun favoritesLoaded(times: Int) {

    }

    fun movieDetailOpened() {
        
    }

    fun movieFavoritesOpened() {
        
    }

}