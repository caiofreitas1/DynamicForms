package com.example.dynamicforms.utils

import android.content.Context
import androidx.annotation.RawRes
import com.example.dynamicforms.data.model.FormDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object FormJsonParser {

    private val moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private val formDtoAdapter by lazy {
        moshi.adapter(FormDto::class.java)
    }

    fun fromRaw(context: Context, @RawRes rawResId: Int): FormDto? {
        val json =
            context.resources.openRawResource(rawResId).bufferedReader().use { it.readText() }

        return formDtoAdapter.fromJson(json)
    }

    fun parseFieldValues(json: String): Map<String, String> {
        val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val adapter = moshi.adapter<Map<String, String>>(type)
        return adapter.fromJson(json) ?: emptyMap()
    }

    fun mapToJson(map: Map<String, String>): String {
        val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val adapter = moshi.adapter<Map<String, String>>(type)
        return adapter.toJson(map)
    }
}
