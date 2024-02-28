package com.vlv.common.ui.adapter.series

import android.view.View
import android.view.ViewGroup
import com.vlv.ui.R
import com.vlv.common.ui.adapter.ErrorViewHolder
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.extensions.inflate
import com.vlv.imperiya.core.ui.warning.SmallWarningView

class SeriesLoaderAdapter(
    retry: () -> Unit
) : LoaderAdapter(
    R.layout.common_series_pagination_item_loading,
    retry
) {

    override fun createErrorViewHolder(parent: ViewGroup) =
        SeriesErrorViewHolder(parent.inflate(R.layout.common_series_pagination_item_error))

}

class SeriesErrorViewHolder(view: View): ErrorViewHolder(view) {

    override fun bind(retry: () -> Unit) {
        (itemView as? SmallWarningView)?.apply {
            setOnClickLink {
                retry.invoke()
            }
        }
    }

}