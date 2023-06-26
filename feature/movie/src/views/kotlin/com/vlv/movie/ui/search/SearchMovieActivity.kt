package com.vlv.movie.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.ImperiyaSearchView
import com.vlv.movie.R

class SearchMovieActivity : AppCompatActivity(R.layout.search_activity) {

    private val search: ImperiyaSearchView by viewProvider(R.id.search)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        search.searchIcon {
            finishAfterTransition()
        }
    }

}