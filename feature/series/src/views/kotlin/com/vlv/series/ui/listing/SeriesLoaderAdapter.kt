package com.vlv.series.ui.listing

import android.view.View
import android.view.ViewGroup
import com.vlv.common.ui.adapter.ErrorViewHolder
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.extensions.inflate
import com.vlv.imperiya.ui.warning.SmallWarningView
import com.vlv.series.R

class SeriesLoaderAdapter(
    retry: () -> Unit
) : LoaderAdapter(
    R.layout.series_pagination_item_loading,
    retry
) {

    override fun createErrorViewHolder(parent: ViewGroup) =
        SeriesErrorViewHolder(parent.inflate(R.layout.series_pagination_item_error))

}

class SeriesErrorViewHolder(view: View): ErrorViewHolder(view) {

    override fun bind(retry: () -> Unit) {
        (itemView as? SmallWarningView)?.apply {
            setOnTryAgain {
                retry.invoke()
            }
        }
    }

}