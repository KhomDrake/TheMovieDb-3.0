package com.vlv.imperiyasample.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.SearchComponent

@Composable
fun MaterialDefaultQuery() {
    var language by remember {
        mutableStateOf("Testing")
    }

    var isActive by remember {
        mutableStateOf(false)
    }

    SearchComponent(
        query = language,
        hint = "Write a language",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        onQueryChange = {
            language = it
        },
        onSearch = {
            language += "test"
        },
        onActiveChange = {
            isActive = it
        },
        active = isActive,
    )
}