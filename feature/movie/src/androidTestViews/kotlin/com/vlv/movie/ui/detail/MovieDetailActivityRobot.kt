package com.vlv.movie.ui.detail

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun MovieDetailActivityTest.movieDetailActivity(func: MovieDetailActivitySetup.() -> Unit) =
    MovieDetailActivitySetup().apply(func)

class MovieDetailActivitySetup : Setup<MovieDetailActivityLaunch, MovieDetailActivityCheck> {
    override fun createCheck(): MovieDetailActivityCheck {
        return MovieDetailActivityCheck()
    }

    override fun createLaunch(): MovieDetailActivityLaunch {
        return MovieDetailActivityLaunch()
    }

    override fun setupLaunch() {

    }
}

class MovieDetailActivityLaunch : Launch<MovieDetailActivityCheck> {
    override fun createCheck(): MovieDetailActivityCheck {
        return MovieDetailActivityCheck()
    }

}

class MovieDetailActivityCheck : Check {

}