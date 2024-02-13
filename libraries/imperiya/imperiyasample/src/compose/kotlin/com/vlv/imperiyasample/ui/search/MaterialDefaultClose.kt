package com.vlv.imperiyasample.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.SearchCloseComponent
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun MaterialDefaultClose() {
    var language by remember {
        mutableStateOf("")
    }

    var active by remember { mutableStateOf(false) }

    SearchCloseComponent(
        query = language,
        hint = "Write a language",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        onSearch = {
            active = false
        },
        onQueryChange = {
            language = it
        },
        onActiveChange = {
           active = it
        },
        active = active,
        content = {
            LazyColumn(
                content = {
                    items(3) {
                        Text(
                            text = it.toString(),
                            style = TheMovieDbTypography.ParagraphBoldStyle,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            )
        },
        onClickClose = {
            language += "ended"
        }
    )
}