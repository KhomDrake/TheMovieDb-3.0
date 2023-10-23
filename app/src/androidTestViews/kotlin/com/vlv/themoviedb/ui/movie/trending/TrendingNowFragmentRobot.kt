package com.vlv.themoviedb.ui.movie.trending

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun TrendingNowFragmentTest.trendingNowFragment(func: TrendingNowFragmentSetup.() -> Unit) =
    TrendingNowFragmentSetup().apply(func)

class TrendingNowFragmentSetup : Setup<TrendingNowFragmentLaunch, TrendingNowFragmentCheck> {
    override fun createCheck(): TrendingNowFragmentCheck {
        return TrendingNowFragmentCheck()
    }

    override fun createLaunch(): TrendingNowFragmentLaunch {
        return TrendingNowFragmentLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withTrendingNowSuccess() {
        
    }

    fun withTrendingNowEmpty() {
        
    }

    fun withTrendingNowFails() {
        
    }

}

class TrendingNowFragmentLaunch : Launch<TrendingNowFragmentCheck> {
    override fun createCheck(): TrendingNowFragmentCheck {
        return TrendingNowFragmentCheck()
    }

    fun clickMovie(position: Int) {

    }

    fun clickSeeAll() {
        
    }

    fun clickTryAgain() {
        
    }
}

class TrendingNowFragmentCheck : Check {
    fun moviesDisplayed() {
        
    }

    fun movieDetailOpened() {
        
    }

    fun movieTrendingNowOpened() {
        
    }

    fun emptyStateDisplayed() {
        
    }

    fun errorStateDisplayed() {
        
    }

    fun moviesTrendingNowLoaded(times: Int) {

    }

}