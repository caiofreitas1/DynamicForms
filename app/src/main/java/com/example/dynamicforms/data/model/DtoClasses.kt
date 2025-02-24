package com.example.dynamicforms.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FormDto(
    @Json(name = "title") val title: String,
    @Json(name = "fields") val fields: List<FieldDto> = emptyList(),
    @Json(name = "section") val section: List<SectionDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class FieldDto(
    @Json(name = "type") val type: String,
    @Json(name = "label") val label: String,
    @Json(name = "name") val name: String,
    @Json(name = "required") val required: Boolean,
    @Json(name = "uuid") val uuid: String,
    @Json(name = "options") val options: List<OptionDto>? = null
)

@JsonClass(generateAdapter = true)
data class OptionDto(
    @Json(name = "label") val label: String,
    @Json(name = "value") val value: String
)

@JsonClass(generateAdapter = true)
data class SectionDto(
    @Json(name = "title") val title: String,
    @Json(name = "from") val from: Int,
    @Json(name = "to") val to: Int,
    @Json(name = "index") val index: Int,
    @Json(name = "uuid") val uuid: String
)
