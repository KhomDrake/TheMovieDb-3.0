package com.vlv.common.ui.adapter.movie

import android.view.View
import android.view.ViewGroup
import com.vlv.common.R
import com.vlv.common.ui.adapter.ErrorViewHolder
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.extensions.inflate
import com.vlv.imperiya.ui.warning.SmallWarningView

class MovieLoaderAdapter(
    retry: () -> Unit
) : LoaderAdapter(
    R.layout.common_movie_pagination_item_loading,
    retry
) {

    override fun createErrorViewHolder(parent: ViewGroup) =
        MovieErrorViewHolder(parent.inflate(R.layout.common_movie_pagination_item_error))

}

class MovieErrorViewHolder(view: View): ErrorViewHolder(view) {

    override fun bind(retry: () -> Unit) {
        (itemView as? SmallWarningView)?.apply {
            setOnClickLink {
                retry.invoke()
            }
        }
    }

}