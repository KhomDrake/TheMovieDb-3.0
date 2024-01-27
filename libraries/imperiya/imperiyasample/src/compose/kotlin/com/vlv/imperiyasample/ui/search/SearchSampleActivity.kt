package com.vlv.imperiyasample.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchSample(paddingValues: PaddingValues) {

    val items = listOf("Material Default - No query", "Material Default", "Material Default - Close")

    val pagerState = rememberPagerState(
        pageCount = {items.size}
    )

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding()
            )
    ) {
        TabRow(
            itemCount = items.size,
            currentIndex = pagerState.currentPage,
            onClick = { newIndex ->
                coroutineScope.launch {
                    pagerState.scrollToPage(newIndex)
                }
            }
        ) { index, isSelected ->
            TabItem(name = items[index], isSelected = isSelected)
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
            )
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when(index) {
                    0 -> {
                        MaterialDefault()
                    }
                    1 -> {
                        MaterialDefaultQuery()
                    }
                    else -> {
                        MaterialDefaultClose()
                    }
                }
            }
        }
    }

}