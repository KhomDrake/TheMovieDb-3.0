package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(
    hint: String,
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    textColor: Color = MaterialTheme.colorScheme.onTertiary,
    changeActive: Boolean = true,
    leadingIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = textColor
        )
    },
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
    enable: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    SearchBar(
        modifier = modifier
            .clickable {
                onClick?.invoke()
            },
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch.invoke(it)
        },
        enabled = enable,
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
            if(changeActive) {
                onActiveChange.invoke(it)
            }
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
        leadingIcon = leadingIcon,
        trailingIcon = {
            if(active) {
                IconButton(
                    onClick = {
                        if(query.isNotEmpty()) {
                            onQueryChange.invoke("")
                        } else {
                            onActiveChange.invoke(false)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(
                            id = R.string.imperiya_search_view_close_description
                        ),
                        tint = textColor
                    )
                }
            }
        },
        content = {
            content.invoke()
        }
    )
}

@Composable
fun SearchCloseComponent(
    hint: String,
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onFocus: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    textColor: Color = MaterialTheme.colorScheme.onTertiary,
    changeActive: Boolean = true,
    onClickClose: () -> Unit = {},
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    SearchComponent(
        hint,
        modifier,
        query,
        onQueryChange,
        onSearch,
        onFocus,
        backgroundColor,
        textColor,
        changeActive,
        active = active,
        onActiveChange = onActiveChange,
        content = content,
        leadingIcon = {
            IconButton(
                onClick = {
                    onClickClose.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(
                        id = R.string.imperiya_search_view_back_description
                    ),
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    )
}

@Composable
fun SearchComponent(
    hint: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enable: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    textColor: Color = MaterialTheme.colorScheme.onTertiary
) {
    SearchComponent(
        hint = hint,
        modifier = modifier,
        onClick = onClick,
        backgroundColor = backgroundColor,
        textColor = textColor,
        content = { },
        changeActive = false,
        enable = enable
    )
}

@PreviewLightDark
@PreviewFontScale
@Preview(name = "Search")
@Composable
fun SearchViewPreview() {
    TheMovieDbAppTheme {
        var language by remember {
            mutableStateOf("")
        }

        SearchComponent(
            query = language,
            hint = "Write a language",
            modifier = Modifier,
            onQueryChange = {
                language = it
            },
            onSearch = {
                language += "test"
            }
        )
    }
}

@PreviewFontScale
@Composable
fun SearchViewWithTextPreview() {
    TheMovieDbAppTheme {
        var language by remember {
            mutableStateOf("kotlin")
        }

        SearchComponent(
            query = language,
            hint = "Write a language",
            modifier = Modifier,
            onQueryChange = {
                language = it
            },
            onSearch = {
                language += "test"
            }
        )
    }
}