package com.vlv.common.extension

import com.vlv.data.local.datastore.DataVault
import com.vlv.data.database.data.ImageType

fun String.toUrlMovieDb(imageType: ImageType = ImageType.POSTER) = runCatching {
    "${DataVault.cachedDataString(ImageType.SECURE_BASE_URL.name)}${
        DataVault.cachedDataString(
            imageType.name
        )
    }$this"
}.getOrNull()