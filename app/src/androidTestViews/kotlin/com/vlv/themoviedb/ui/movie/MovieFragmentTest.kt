package com.vlv.themoviedb.ui.movie

import com.vlv.data.network.NetworkInitializer
import com.vlv.favorite.FavoriteInitializer
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import com.vlv.movie.presentation.MovieInitializer
import com.vlv.movie.data.repository.MovieRepository
import com.vlv.tv_show.SeriesInitializer
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import io.mockk.mockk
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<MovieRepository>(relaxed = true) }
    single { mockk<SeriesFavoriteUseCase>(relaxed = true) }
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
    @Ignore("Not working on CI ")
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