package com.vlv.extensions

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingDataAdapter

fun PagingDataAdapter<*, *>.setupState(
    onData: () -> Unit,
    onError: (Throwable?) -> Unit,
    onEmpty: () -> Unit,
    onLoading: () -> Unit
) {
    addLoadStateListener { combinedState ->
        val source = combinedState.source
        when {
            source.notLoading() -> Unit
            source.refresh == LoadState.Loading -> {
                onLoading.invoke()
            }
            source.refresh is LoadState.Error -> {
                val throwable = (source.refresh as? LoadState.Error)?.error
                onError.invoke(throwable)
            }
            source.prepend.fullyLoaded() && source.append.fullyLoaded() && itemCount == 0 -> {
                onEmpty.invoke()
            }
            source.prepend is LoadState.NotLoading -> {
                onData.invoke()
            }
            else -> Unit
        }
    }
}

fun LoadState.fullyLoaded() : Boolean {
    return this is LoadState.NotLoading && endOfPaginationReached
}

fun LoadState.notLoading() : Boolean {
    return this is LoadState.NotLoading && !endOfPaginationReached
}

fun LoadStates.notLoading() : Boolean {
    return refresh.notLoading() && append.notLoading() && prepend.notLoading()
}