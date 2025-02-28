package com.example.dynamicforms.data.repository

import com.example.dynamicforms.data.datasource.FormLocalDataSource
import com.example.dynamicforms.data.model.EntryEntity
import com.example.dynamicforms.data.model.FormDto
import com.example.dynamicforms.data.model.toEntities
import com.example.dynamicforms.domain.Entry
import com.example.dynamicforms.domain.Field
import com.example.dynamicforms.domain.Form
import com.example.dynamicforms.domain.FormRepository
import com.example.dynamicforms.domain.Option
import com.example.dynamicforms.domain.Section
import com.example.dynamicforms.utils.FormJsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FormRepositoryImpl @Inject constructor(
    private val localDataSource: FormLocalDataSource
) : FormRepository {
    override suspend fun insertFormFromDto(formId: String, formDto: FormDto) {
        withContext(Dispatchers.IO) {
            val (formEntity, sectionEntities, fieldsWithOptions) = formDto.toEntities(formId)
            localDataSource.insertForms(listOf(formEntity))
            localDataSource.insertSections(sectionEntities)

            val fieldEntities = fieldsWithOptions.map { it.field }
            localDataSource.insertFields(fieldEntities)

            fieldsWithOptions.forEach { field ->
                if (field.options.isNotEmpty()) {
                    localDataSource.insertOptions(field.options)
                }
            }
        }
    }

    override suspend fun getAllForms(): List<Form> {
        return withContext(Dispatchers.IO) {
            localDataSource.getAllForms().map { formEntity ->
                Form(
                    formId = formEntity.formId,
                    title = formEntity.title
                )
            }
        }
    }

    override suspend fun getFormContents(formId: String): Triple<Form, List<Section>, List<Field>>? {
        return withContext(Dispatchers.IO) {
            val formEntity = localDataSource.getFormById(formId) ?: return@withContext null
            val sectionEntities = localDataSource.getSectionsByFormId(formId)
            val fieldEntities = localDataSource.getFieldsByFormId(formId)

            val domainForm = Form(
                formId = formEntity.formId,
                title = formEntity.title
            )

            val domainSections = sectionEntities.map { sectionEntity ->
                Section(
                    sectionId = sectionEntity.sectionId,
                    parentFormId = sectionEntity.parentFormId,
                    title = sectionEntity.title,
                    fromIndex = sectionEntity.fromIndex,
                    toIndex = sectionEntity.toIndex,
                    index = sectionEntity.index
                )
            }

            val domainFields = fieldEntities.map { fieldEntity ->
                val options =
                    localDataSource.getOptionsByFieldId(fieldEntity.fieldId).map { optionEntity ->
                        Option(
                            optionId = optionEntity.optionId,
                            label = optionEntity.label,
                            value = optionEntity.value
                        )
                    }

                Field(
                    fieldId = fieldEntity.fieldId,
                    parentFormId = fieldEntity.parentFormId,
                    type = fieldEntity.type,
                    label = fieldEntity.label,
                    name = fieldEntity.name,
                    required = fieldEntity.required,
                    orderIndex = fieldEntity.orderIndex,
                    options = options
                )
            }
            Triple(domainForm, domainSections, domainFields)
        }
    }

    override suspend fun getEntriesByFormId(formId: String): List<Entry> {
        return withContext(Dispatchers.IO) {
            val entryEntities = localDataSource.getEntriesByFormId(formId)

            entryEntities.map { entryEntity ->
                val valuesMap: Map<String, String> = FormJsonParser.parseFieldValues(
                    entryEntity.fieldValuesJson
                )
                Entry(
                    entryId = entryEntity.entryId,
                    formId = entryEntity.formId,
                    fieldValues = valuesMap,
                    timestamp = entryEntity.timestamp
                )
            }
        }
    }

    override suspend fun saveEntry(
        formId: String,
        entryId: String,
        fieldValues: Map<String, String>
    ) {
        withContext(Dispatchers.IO) {
            val json = FormJsonParser.mapToJson(fieldValues)

            val entity = EntryEntity(
                entryId = entryId,
                formId = formId,
                fieldValuesJson = json,
                timestamp = System.currentTimeMillis()
            )

            localDataSource.insertEntry(entity)
        }
    }
}
