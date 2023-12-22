package com.vlv.libraries.sample.bondsmith

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.arch.toolkit.common.DataResultStatus
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.libraries.sample.SampleActivity
import com.vlv.libraries.sample.TheMovieDbButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class BondsmithActivity : SampleActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                DefaultTopBar(title = "Bondsmith") {
                    finish()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding() + 12.dp)
            ) {
                val tabs = listOf("Flow", "Flow no cache", "LiveData", "LiveData no cache")
                val scope = rememberCoroutineScope()
                val pagerState = rememberPagerState(pageCount = { tabs.size })
                TabRow(
                    itemCount = tabs.size,
                    currentIndex = pagerState.currentPage,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(it)
                        }
                    }
                ) { index, isSelected ->
                    val item = tabs[index]
                    TabItem(name = item, isSelected = isSelected)
                }

                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 16.dp,
                    modifier = Modifier.fillMaxSize()
                ) { index ->
                    when(index) {
                        0 -> {
                            FlowScreen(paddingValues = it)
                        }
                        1 -> {
                            FlowScreen(
                                withCache = false,
                                paddingValues = it
                            )
                        }
                        2 -> {
                            LiveDataScreen(paddingValues = it)
                        }
                        else -> {
                            LiveDataScreen(
                                withCache = false,
                                paddingValues = it
                            )
                        }
                    }
                }
            }


        }
    }

}

@Composable
fun LiveDataScreen(
    paddingValues: PaddingValues,
    withCache: Boolean = true,
    viewModel: BondsmithViewModel = koinViewModel()
) {
    val state by viewModel.liveData.observeAsState()

    LaunchedEffect(key1 = 2, block = {
        viewModel.responseLiveData(
            withCache = withCache
        )
    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
    ) {
        TheMovieDbButton(
            title = "Error",
            onClick = {
                viewModel.responseLiveData(
                    error = true,
                    withCache = withCache
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = state.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = TheMovieDbTypography.ParagraphBoldStyle
        )

        when(state?.status ?: DataResultStatus.LOADING) {
            DataResultStatus.SUCCESS -> {
                SmallWarningView(
                    title = "State Data",
                    body = state?.data.toString(),
                    linkActionText = "Load again",
                    onClickLink = {
                        viewModel.responseLiveData(
                            withCache = withCache
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            DataResultStatus.ERROR -> {
                SmallWarningView(
                    title = "State Error",
                    body = state?.error.toString(),
                    linkActionText = "Load again",
                    onClickLink = {
                        viewModel.responseLiveData(
                            withCache = withCache
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            DataResultStatus.LOADING -> {
                SmallWarningView(
                    title = "State Loading",
                    body = null,
                    linkActionText = "Load again",
                    onClickLink = {
                        viewModel.responseLiveData(
                            withCache = withCache
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FlowScreen(
    paddingValues: PaddingValues,
    withCache: Boolean = true,
    viewModel: BondsmithViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = 2, block = {
        viewModel.flow(
            withCache = withCache
        )
    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
    ) {
        TheMovieDbButton(
            title = "Error",
            onClick = {
                viewModel.flow(error = true, withCache = withCache)
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = state.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = TheMovieDbTypography.ParagraphBoldStyle
        )

        when(state.state) {
            ResponseStatus.SUCCESS -> {
                SmallWarningView(
                    title = "State Data",
                    body = state.data.toString(),
                    linkActionText = "Load again",
                    onClickLink = {
                        viewModel.flow(
                            withCache = withCache
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            ResponseStatus.ERROR -> {
                SmallWarningView(
                    title = "State Error",
                    body = state.error.toString(),
                    linkActionText = "Load again",
                    onClickLink = {
                        viewModel.flow(
                            withCache = withCache
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            ResponseStatus.LOADING -> {
                SmallWarningView(
                    title = "State Loading",
                    body = null,
                    linkActionText = "Load again",
                    onClickLink = {
                        viewModel.flow(
                            withCache = withCache
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
