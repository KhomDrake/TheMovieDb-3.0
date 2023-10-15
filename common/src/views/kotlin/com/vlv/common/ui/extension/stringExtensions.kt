package com.vlv.common.ui.extension

import android.widget.ImageView
import coil.load
import com.vlv.common.R
import com.vlv.network.database.data.ImageType
import com.vlv.network.datastore.DataVault.cachedDataString

private fun String.toUrlMovieDb(imageType: ImageType = ImageType.POSTER) = runCatching {
    "${cachedDataString(ImageType.SECURE_BASE_URL.name)}${cachedDataString(imageType.name)}$this"
}.getOrNull()

fun String?.loadUrl(view: ImageView, imageType: ImageType = ImageType.POSTER) {
    view.load(this?.toUrlMovieDb(imageType) ?: R.drawable.image_default) {
        error(R.drawable.load_image)
        crossfade(1000)
    }
}