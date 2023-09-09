package com.vlv.common.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class LoaderAdapter(
    @LayoutRes
    private val loadingLayout: Int,
    private val createErrorViewHolder: (ViewGroup) -> ErrorViewHolder,
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderViewHolder>() {
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        when(holder) {
            is ErrorViewHolder -> {
                holder.bind(retry)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoaderViewHolder {
        return when(loadState) {
            is LoadState.Error -> {
                createErrorViewHolder.invoke(parent)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    loadingLayout, parent, false
                )
                LoadingViewHolder(view)
            }
        }
    }

}

abstract class LoaderViewHolder(view: View): RecyclerView.ViewHolder(view)

abstract class ErrorViewHolder(view: View): LoaderViewHolder(view) {

    abstract fun bind(retry: () -> Unit)

}

class LoadingViewHolder(view: View): LoaderViewHolder(view)