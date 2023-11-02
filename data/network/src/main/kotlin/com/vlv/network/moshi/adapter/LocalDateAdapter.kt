package com.vlv.network.moshi.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.vlv.extensions.toLocalDate
import org.threeten.bp.LocalDate

class LocalDateAdapter : JsonAdapter<LocalDate>() {

    override fun fromJson(reader: JsonReader): LocalDate? {
        if(reader.peek() == JsonReader.Token.NULL) return reader.nextNull()

        return runCatching {
            val value = reader.nextString()
            value.toLocalDate()
        }.getOrElse {
            reader.nextNull()
        }
    }

    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        runCatching {
            value.toString()
        }.onSuccess {
            writer.value(it)
        }
    }

}