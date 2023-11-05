package com.vlv.data.network.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vlv.data.network.moshi.adapter.LocalDateAdapter
import com.vlv.data.network.moshi.adapter.LocalDateTimeAdapter

object MoshiFactory {

    @OptIn(ExperimentalStdlibApi::class)
    fun moshi(): Moshi = Moshi.Builder().apply {
        addAdapter(LocalDateAdapter())
        addAdapter(LocalDateTimeAdapter())
    }.build()

}