package com.vlv.common.ui.extension

import android.widget.ImageView
import coil.load
import com.vlv.data.local.datastore.DataVault.cachedDataString
import com.vlv.ui.R
import com.vlv.data.network.database.data.ImageType

fun String?.loadUrl(view: ImageView, imageType: ImageType = ImageType.POSTER) {
    view.load(this?.toUrlMovieDb(imageType) ?: R.drawable.image_default) {
        error(R.drawable.load_image)
        crossfade(1000)
    }
}