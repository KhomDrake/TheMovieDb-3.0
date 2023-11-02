package com.vlv.common.ui.adapter.people

import android.view.View
import android.view.ViewGroup
import com.vlv.ui.R
import com.vlv.common.ui.adapter.ErrorViewHolder
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.extensions.inflate
import com.vlv.imperiya.core.ui.warning.SmallWarningView

class PeopleLoaderAdapter(
    retry: () -> Unit
) : LoaderAdapter(
    R.layout.common_people_listing_item_loading,
    retry = retry
) {

    override fun createErrorViewHolder(parent: ViewGroup): ErrorViewHolder {
        return PeopleErrorViewHolder(parent.inflate(R.layout.common_people_pagination_item_error))
    }

}

class PeopleErrorViewHolder(view: View): ErrorViewHolder(view) {

    override fun bind(retry: () -> Unit) {
        (itemView as? SmallWarningView)?.apply {
            setOnClickLink {
                retry.invoke()
            }
        }
    }

}
