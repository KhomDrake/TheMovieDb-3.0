package com.vlv.themoviedb.ui.movie.nowplaying

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun NowPlayingFragmentTest.nowPlayingFragment(func: NowPlayingFragmentSetup.() -> Unit) =
    NowPlayingFragmentSetup().apply(func)

class NowPlayingFragmentSetup : Setup<NowPlayingFragmentLaunch, NowPlayingFragmentCheck> {
    override fun createCheck(): NowPlayingFragmentCheck {
        return NowPlayingFragmentCheck()
    }

    override fun createLaunch(): NowPlayingFragmentLaunch {
        return NowPlayingFragmentLaunch()
    }

    override fun setupLaunch() {
        
    }

    fun withNowPlayingSuccess() {
        
    }

    fun withNowPlayingEmpty() {
        
    }

    fun withNowPlayingFails() {
        
    }

}

class NowPlayingFragmentLaunch : Launch<NowPlayingFragmentCheck> {
    override fun createCheck(): NowPlayingFragmentCheck {
        return NowPlayingFragmentCheck()
    }

    fun clickTryAgain() {

    }

    fun clickMovie(position: Int) {

    }

    fun clickSeeAll() {

    }
}

class NowPlayingFragmentCheck : Check {
    fun moviesDisplayed() {
        
    }

    fun errorStateDisplayed() {

    }

    fun emptyStateDisplayed() {

    }

    fun moviesNowPlayingLoaded(times: Int) {

    }

    fun movieDetailOpened() {

    }

    fun movieNowPlayingOpened() {

    }

}