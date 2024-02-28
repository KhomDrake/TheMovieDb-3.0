package com.vlv.common.ui.extension

import android.widget.ImageView
import coil.load
import com.vlv.common.extension.toUrlMovieDb
import com.vlv.data.database.data.ImageType
import com.vlv.ui.R

fun String?.loadUrl(view: ImageView, imageType: ImageType = ImageType.POSTER) {
    view.load(this?.toUrlMovieDb(imageType) ?: R.drawable.image_default) {
        error(R.drawable.load_image)
        crossfade(1000)
    }
}