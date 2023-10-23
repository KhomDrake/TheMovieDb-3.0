package com.vlv.themoviedb.ui

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun MainActivityTest.mainActivity(func: MainActivitySetup.() -> Unit) =
    MainActivitySetup().apply(func)

class MainActivitySetup : Setup<MainActivityLaunch, MainActivityCheck> {
    override fun createCheck(): MainActivityCheck {
        return MainActivityCheck()
    }

    override fun createLaunch(): MainActivityLaunch {
        return MainActivityLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withMovies() {
        
    }

    fun withSeries() {
        
    }

    fun withSearch() {
        
    }

    fun withFavorites() {
        
    }

}

class MainActivityLaunch : Launch<MainActivityCheck> {
    override fun createCheck(): MainActivityCheck {
        return MainActivityCheck()
    }

    fun clickSeriesBottomNavigation() {
        
    }

    fun clickSearchBottomNavigation() {
        
    }

    fun clickFavoritesBottomNavigation() {
        
    }

    fun clickMenuBottomNavigation() {
        
    }
}

class MainActivityCheck : Check {
    fun moviesDisplayed() {
        
    }

    fun seriesDisplayed() {
        
    }

    fun searchDisplayed() {
        
    }

    fun favoritesDisplayed() {
        
    }

    fun menuDisplayed() {
        
    }
}