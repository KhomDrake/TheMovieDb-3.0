package com.vlv.themoviedb.ui.movie

import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.MovieInitializer
import com.vlv.network.NetworkInitializer
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.repository.MovieRepository
import com.vlv.series.SeriesInitializer
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieRepository>(relaxed = true) }
    single { mockk<TheMovieDbDao>(relaxed = true) }
}

class MovieFragmentTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun checkIfMoviesAllBeingLoaded() {
        movieFragment {
            withFavorites()
            withMovieTrending()
            withMoviePlayingNow()
        } check {
            favoritesLoaded()
            trendingLoaded()
            nowPlayingLoaded()
        }
    }

}