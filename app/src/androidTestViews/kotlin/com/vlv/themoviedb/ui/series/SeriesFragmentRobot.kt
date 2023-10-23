package com.vlv.themoviedb.ui.series

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun SeriesFragmentTest.seriesFragment(func: SeriesFragmentSetup.() -> Unit) =
    SeriesFragmentSetup().apply(func)

class SeriesFragmentSetup : Setup<SeriesFragmentLaunch, SeriesFragmentCheck> {
    override fun createCheck(): SeriesFragmentCheck {
        return SeriesFragmentCheck()
    }

    override fun createLaunch(): SeriesFragmentLaunch {
        return SeriesFragmentLaunch()
    }

    override fun setupLaunch() {

    }

    fun withFavorites() {
        
    }

    fun withSeriesTrending() {
        
    }

    fun withSeriesAiringToday() {
        
    }

}

class SeriesFragmentLaunch : Launch<SeriesFragmentCheck> {
    override fun createCheck(): SeriesFragmentCheck {
        return SeriesFragmentCheck()
    }
}

class SeriesFragmentCheck : Check {
    fun favoritesLoaded() {
        
    }

    fun trendingLoaded() {
        
    }

    fun airingTodayLoaded() {
        
    }
}