package com.vlv.common.ui.extension

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates

fun CombinedLoadStates.isFullLoading() = refresh is LoadState.Loading

fun stateFullLoading() = CombinedLoadStates(
    refresh = LoadState.Loading,
    LoadState.NotLoading(endOfPaginationReached = false),
    LoadState.NotLoading(endOfPaginationReached = false),
    LoadStates(
        LoadState.NotLoading(endOfPaginationReached = false),
        LoadState.NotLoading(endOfPaginationReached = false),
        LoadState.NotLoading(endOfPaginationReached = false)
    ),
    null,
)

fun stateData() = CombinedLoadStates(
    refresh = LoadState.NotLoading(endOfPaginationReached = false),
    prepend = LoadState.NotLoading(endOfPaginationReached = false),
    append = LoadState.NotLoading(endOfPaginationReached = false),
    source = LoadStates(
        LoadState.Loading,
        LoadState.Loading,
        LoadState.Loading
    ),
    mediator = null,
)

fun CombinedLoadStates.isSingleLoading(loadState: LoadState = append) = loadState is LoadState.Loading

fun stateSingleLoading() = CombinedLoadStates(
    refresh = LoadState.NotLoading(endOfPaginationReached = false),
    prepend = LoadState.NotLoading(endOfPaginationReached = false),
    append = LoadState.Loading,
    source = LoadStates(
        LoadState.Loading,
        LoadState.Loading,
        LoadState.Loading
    ),
    mediator = null,
)

fun CombinedLoadStates.isSingleError() = append is LoadState.Error

fun stateSingleError() = CombinedLoadStates(
    refresh = LoadState.NotLoading(endOfPaginationReached = false),
    prepend = LoadState.NotLoading(endOfPaginationReached = false),
    append = LoadState.Error(Throwable()),
    source = LoadStates(
        LoadState.Loading,
        LoadState.Loading,
        LoadState.Loading
    ),
    mediator = null,
)

