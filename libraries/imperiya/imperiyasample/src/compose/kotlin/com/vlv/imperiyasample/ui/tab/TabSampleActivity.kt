package com.vlv.imperiyasample.ui.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import com.vlv.imperiyasample.SampleActivity
import kotlinx.coroutines.launch

class TabSampleActivity : SampleActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                DefaultTopBar(title = "Tab Sample") {
                    finish()
                }
            }
        ) {
            val items = listOf("Text1", "Text2", "Text3")

            val pagerState = rememberPagerState(
                pageCount = {items.size}
            )

            Column(
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding()
                    )
            ) {
                TabRow(
                    itemCount = items.size,
                    currentIndex = pagerState.currentPage,
                    onClick = { newIndex ->
                        lifecycleScope.launch {
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
                ) {
                    val item = items[it]
                    SmallWarningView(
                        modifier = Modifier
                            .fillMaxWidth(),
                        title = "Title $item",
                        body = "Body $item",
                        linkActionText = "Link $item"
                    )
                }
            }
        }
    }

}