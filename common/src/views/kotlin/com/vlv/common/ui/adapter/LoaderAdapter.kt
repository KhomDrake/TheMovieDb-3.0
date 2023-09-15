package com.vlv.common.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlv.extensions.inflate

const val VIEW_TYPE_ERROR = 2342
const val VIEW_TYPE_LOADING = 2343

abstract class LoaderAdapter(
    @LayoutRes
    private val loadingLayout: Int,
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        when(holder) {
            is ErrorViewHolder -> {
                holder.bind(retry)
            }
            else -> Unit
        }
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return when(loadState) {
            is LoadState.Error -> VIEW_TYPE_ERROR
            else -> VIEW_TYPE_LOADING
        }
    }

    abstract fun createErrorViewHolder(parent: ViewGroup): ErrorViewHolder

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoaderViewHolder {
        return when(loadState) {
            is LoadState.Error -> {
                createErrorViewHolder(parent)
            }
            else -> LoadingViewHolder(parent.inflate(loadingLayout))
        }
    }

}

abstract class LoaderViewHolder(view: View): RecyclerView.ViewHolder(view)

abstract class ErrorViewHolder(view: View): LoaderViewHolder(view) {

    abstract fun bind(retry: () -> Unit)

}

class LoadingViewHolder(view: View): LoaderViewHolder(view)