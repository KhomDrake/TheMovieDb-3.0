package com.vlv.imperiyasample.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

class SearchSampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = "Search Components") {
                            finish()
                        }
                    }
                ) {
                    SearchSample(paddingValues = it)
                }
            }
        }
    }

}

@Composable
fun SearchSample(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding()
            )
    ) {
        var language by remember {
            mutableStateOf("")
        }

        SearchComponent(
            query = language,
            hint = "Write a language",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onQueryChange = {
                language = it
            },
            onSearch = {
                language += "test"
            }
        )

        SearchComponent(
            hint = "Write a language",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onSearch = {
                language += "test2"
            }
        )
    }
}