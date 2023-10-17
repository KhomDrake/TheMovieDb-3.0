package com.vlv.movie.ui.detail.cast

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun MovieCastFragmentTest.movieCastFragment(func: MovieCastFragmentSetup.() -> Unit) =
    MovieCastFragmentSetup().apply(func)

class MovieCastFragmentSetup : Setup<MovieCastFragmentLaunch, MovieCastFragmentCheck> {

    override fun createCheck(): MovieCastFragmentCheck {
        return MovieCastFragmentCheck()
    }

    override fun createLaunch(): MovieCastFragmentLaunch {
        return MovieCastFragmentLaunch()
    }

    override fun setupLaunch() {

    }

}

class MovieCastFragmentLaunch : Launch<MovieCastFragmentCheck> {

    override fun createCheck(): MovieCastFragmentCheck {
        return MovieCastFragmentCheck()
    }

}

class MovieCastFragmentCheck : Check {
}