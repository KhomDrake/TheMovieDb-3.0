package com.vlv.themoviedb.ui.series.favorites

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun SeriesFavoritesFragmentTest.seriesFavoritesFragment(func: SeriesFavoritesFragmentSetup.() -> Unit) =
    SeriesFavoritesFragmentSetup().apply(func)

class SeriesFavoritesFragmentSetup : Setup<SeriesFavoritesFragmentLaunch, SeriesFavoritesFragmentCheck> {
    override fun createCheck(): SeriesFavoritesFragmentCheck {
        return SeriesFavoritesFragmentCheck()
    }

    override fun createLaunch(): SeriesFavoritesFragmentLaunch {
        return SeriesFavoritesFragmentLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withFavoritesSuccess() {
        
    }

    fun withFavoritesEmpty() {
        
    }

    fun withFavoritesError() {
        
    }
}

class SeriesFavoritesFragmentLaunch : Launch<SeriesFavoritesFragmentCheck> {

    override fun createCheck(): SeriesFavoritesFragmentCheck {
        return SeriesFavoritesFragmentCheck()
    }

    fun clickTryAgain() {
        
    }

    fun clickFavorite(position: Int) {

    }

    fun clickSeeAll() {
        
    }

}

class SeriesFavoritesFragmentCheck : Check {
    fun favoritesDisplayed() {
        
    }

    fun emptyStateDisplayed() {
        
    }

    fun errorStateDisplayed() {
        
    }

    fun favoritesLoaded(times: Int) {

    }

    fun seriesDetailOpened() {
        
    }

    fun seriesFavoritesOpened() {
        
    }

}