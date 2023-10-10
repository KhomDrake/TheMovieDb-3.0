package com.vlv.common.ui.extension

import android.widget.ImageView
import coil.load
import com.vlv.network.database.data.ImageType
import com.vlv.network.datastore.DataVault.cachedData

fun String.toUrlMovieDb(imageType: ImageType = ImageType.POSTER) = kotlin.run {
    "${cachedData(ImageType.SECURE_BASE_URL.name)}${cachedData(imageType.name)}$this"
}

fun String.loadUrl(view: ImageView, imageType: ImageType = ImageType.POSTER) {
    view.load(this.toUrlMovieDb(imageType)) {
        crossfade(1000)
    }
}