package com.vlv.themoviedb.ui.series.airingtoday

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun AiringTodayFragmentTest.airingTodayFragment(func: AiringTodayFragmentSetup.() -> Unit) =
    AiringTodayFragmentSetup().apply(func)

class AiringTodayFragmentSetup : Setup<AiringTodayFragmentLaunch, AiringTodayFragmentCheck> {
    override fun createCheck(): AiringTodayFragmentCheck {
        return AiringTodayFragmentCheck()
    }

    override fun createLaunch(): AiringTodayFragmentLaunch {
        return AiringTodayFragmentLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withAiringTodaySuccess() {
        
    }

    fun withAiringTodayEmpty() {
        
    }

    fun withAiringTodayFails() {
        
    }
}

class AiringTodayFragmentLaunch : Launch<AiringTodayFragmentCheck> {
    override fun createCheck(): AiringTodayFragmentCheck {
        return AiringTodayFragmentCheck()
    }

    fun clickSeries(position: Int) {

    }

    fun clickSeeAll() {
        
    }

    fun clickTryAgain() {
        
    }
}

class AiringTodayFragmentCheck : Check {
    fun seriesDisplayed() {
        
    }

    fun seriesDetailOpened() {
        
    }

    fun seriesAiringTodayOpened() {
        
    }

    fun emptyStateDisplayed() {
        
    }

    fun errorStateDisplayed() {
        
    }

    fun seriesAiringTodayLoaded(times: Int) {

    }

}