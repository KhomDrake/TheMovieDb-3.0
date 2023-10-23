package com.vlv.themoviedb.ui.series.trending

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun TrendingFragmentTest.trendingFragment(func: TrendingFragmentSetup.() -> Unit) =
    TrendingFragmentSetup().apply(func)

class TrendingFragmentSetup : Setup<TrendingFragmentLaunch, TrendingFragmentCheck> {
    override fun createCheck(): TrendingFragmentCheck {
        return TrendingFragmentCheck()
    }

    override fun createLaunch(): TrendingFragmentLaunch {
        return TrendingFragmentLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withTrendingSuccess() {
        
    }

    fun withTrendingEmpty() {
        
    }

    fun withTrendingFails() {
        
    }

}

class TrendingFragmentLaunch : Launch<TrendingFragmentCheck> {
    override fun createCheck(): TrendingFragmentCheck {
        return TrendingFragmentCheck()
    }

    fun clickSeries(position: Int) {

    }

    fun clickSeeAll() {
        
    }

    fun clickTryAgain() {
        
    }
}

class TrendingFragmentCheck : Check {
    fun seriesDisplayed() {
        
    }

    fun seriesDetailOpened() {
        
    }

    fun seriesTrendingOpened() {
        
    }

    fun emptyStateDisplayed() {
        
    }

    fun errorStateDisplayed() {
        
    }

    fun seriesTrendingLoaded(times: Int) {

    }

}