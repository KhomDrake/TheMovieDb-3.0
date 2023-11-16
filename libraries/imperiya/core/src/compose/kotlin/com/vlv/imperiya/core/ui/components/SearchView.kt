package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(
    hint: String,
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    content: @Composable () -> Unit = {}
) {
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        colors = SearchBarDefaults.colors(
            containerColor = backgroundColor,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                cursorColor = textColor
            )
        ),
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .alpha(.6f),
                text = hint,
                style = TheMovieDbTypography.ParagraphStyle,
                color = textColor
            )
        },
        leadingIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = textColor
                )
            }
        },
        trailingIcon = {
            if(active) {
                IconButton(
                    onClick = {
                        if(query.isNotEmpty()) {
                            onQueryChange.invoke("")
                        } else {
                            active = false
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = textColor
                    )
                }
            }
        }
    ) {
        content.invoke()
    }
}

@Preview
@Composable
fun SearchViewPreview() {
    TheMovieDbAppTheme {
        var language by remember {
            mutableStateOf("kotlin")
        }

        SearchComponent(
            query = language,
            hint = "Write a language",
            modifier = Modifier
                .fillMaxWidth(),
            onQueryChange = {
                language = it
            },
            onSearch = {
                language += "test"
            }
        )
    }
}