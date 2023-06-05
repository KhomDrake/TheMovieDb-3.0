package com.vlv.network.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiFactory {

    fun moshi(): Moshi {
        return Moshi.Builder().apply {
//            addLast(KotlinJsonAdapterFactory())
        }.build()
    }

}