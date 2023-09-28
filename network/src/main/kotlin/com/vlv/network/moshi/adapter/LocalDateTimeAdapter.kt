package com.vlv.network.moshi.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.vlv.extensions.toLocalDateTime
import org.threeten.bp.LocalDateTime

class LocalDateTimeAdapter: JsonAdapter<LocalDateTime>() {

    override fun fromJson(reader: JsonReader): LocalDateTime? {
        if(reader.peek() == JsonReader.Token.NULL) return reader.nextNull()

        return runCatching {
            val value = reader.nextString()
            value.toLocalDateTime()
        }.getOrElse {
            reader.nextNull()
        }
    }

    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        runCatching {
            value.toString()
        }.onSuccess {
            writer.value(it)
        }
    }

}