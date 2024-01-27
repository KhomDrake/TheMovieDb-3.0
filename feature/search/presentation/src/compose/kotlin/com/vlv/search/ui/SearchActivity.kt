package com.vlv.search.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.vlv.common.route.SEARCH_TYPE_EXTRA
import com.vlv.common.route.SearchType
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

class SearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val type = intent.extras?.getString(SEARCH_TYPE_EXTRA) ?: SearchType.MOVIE.name
            TheMovieDbAppTheme {
                SearchComponent(
                    hint = "Test",
                    modifier = Modifier
                        .fillMaxWidth(),
                    onQueryChange = {

                    },
                    onSearch = {
                        
                    }
                )
            }
        }
    }

}