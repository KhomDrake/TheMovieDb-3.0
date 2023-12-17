package com.vlv.common.ui.extension

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.isFullLoading() = refresh is LoadState.Loading

fun CombinedLoadStates.isSingleLoading() = append is LoadState.Loading

fun CombinedLoadStates.isSingleError() = append is LoadState.Error

