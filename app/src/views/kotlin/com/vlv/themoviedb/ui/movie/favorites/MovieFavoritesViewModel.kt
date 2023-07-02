package com.vlv.themoviedb.ui.movie.favorites

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import kotlinx.coroutines.delay

class MovieFavoritesViewModel : ViewModel() {

    fun favorites() = bondsmith<List<String>>()
        .request {
            delay(100)
            listOf()
        }
        .execute()
        .responseLiveData


}