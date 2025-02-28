package com.example.dynamicforms.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class FormDto(
    @Json(name = "title") val title: String,
    @Json(name = "fields") val fields: List<FieldDto> = emptyList(),
    @Json(name = "sections") val sections: List<SectionDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class FieldDto(
    @Json(name = "type") val type: String,
    @Json(name = "label") val label: String,
    @Json(name = "name") val name: String,
    @Json(name = "required") val required: Boolean? = false,
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

fun FormDto.toEntities(formId: String): Triple<FormEntity, List<SectionEntity>, List<FieldEntityWithOptions>> {
    val formEntity = FormEntity(
        formId = formId,
        title = title
    )

    val sectionEntities = sections.map { section ->
        SectionEntity(
            sectionId = section.uuid,
            parentFormId = formId,
            title = section.title,
            fromIndex = section.from,
            toIndex = section.to,
            index = section.index
        )

    }

    val fieldsWithOptions = fields.mapIndexed { index, field ->
        val fieldEntity = FieldEntity(
            fieldId = field.uuid,
            parentFormId = formId,
            type = field.type,
            label = field.label,
            name = field.name,
            required = field.required ?: false,
            orderIndex = index
        )

        val optionEntities = field.options?.map { optionDto ->
            OptionEntity(
                optionId = UUID.randomUUID().toString(),
                parentFieldId = fieldEntity.fieldId,
                label = optionDto.label,
                value = optionDto.value
            )
        }.orEmpty()

        return@mapIndexed FieldEntityWithOptions(fieldEntity, optionEntities)
    }

    return Triple(formEntity, sectionEntities, fieldsWithOptions)

}

data class FieldEntityWithOptions(
    val field: FieldEntity,
    val options: List<OptionEntity>
)
