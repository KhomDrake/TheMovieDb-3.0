package com.vlv.themoviedb.ui.movie

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toFavorite
import com.vlv.data.network.model.movie.MoviesResponse
import com.vlv.data.network.database.TheMovieDbDao
import com.vlv.data.network.database.data.FavoriteType
import com.vlv.data.network.repository.MovieRepository
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.loadObjectFromJson
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun MovieFragmentTest.movieFragment(func: MovieFragmentSetup.() -> Unit) =
    MovieFragmentSetup().apply(func)

class MovieFragmentSetup : Setup<MovieFragmentLaunch, MovieFragmentCheck>, KoinComponent {

    private val repository: MovieRepository by inject()
    private val theMovieDbDao: TheMovieDbDao by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): MovieFragmentCheck {
        return MovieFragmentCheck()
    }

    override fun createLaunch(): MovieFragmentLaunch {
        return MovieFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<MovieFragment>(
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withFavorites() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_list.json",
            moshi
        ) ?: return

        coEvery {
            theMovieDbDao.favoriteByType(FavoriteType.MOVIE)
        } returns data.movies.map { Movie(it).toFavorite() }
    }

    fun withMovieTrending() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_list.json",
            moshi
        ) ?: return

        coEvery {
            repository.trendingMovies(any())
        } returns data
    }

    fun withMoviePlayingNow() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movies_list.json",
            moshi
        ) ?: return

        coEvery {
            repository.nowPlaying()
        } returns data
    }

}

class MovieFragmentLaunch : Launch<MovieFragmentCheck> {
    override fun createCheck(): MovieFragmentCheck {
        return MovieFragmentCheck()
    }
}

class MovieFragmentCheck : Check, KoinComponent {

    private val repository: MovieRepository by inject()
    private val theMovieDbDao: TheMovieDbDao by inject()

    fun favoritesLoaded() {
        coVerify {
            theMovieDbDao.favoriteByType(FavoriteType.MOVIE)
        }
    }

    fun nowPlayingLoaded() {
        coVerify {
            repository.nowPlaying()
        }
    }

    fun trendingLoaded() {
        coVerify {
            repository.trendingMovies(any())
        }
    }

}