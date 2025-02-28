package com.example.dynamicforms.domain

import com.example.dynamicforms.data.model.FormDto

interface FormRepository {
    suspend fun insertFormFromDto(formId: String, formDto: FormDto)
    suspend fun getAllForms(): List<Form>
    suspend fun getFormContents(formId: String): Triple<Form, List<Section>, List<Field>>?
    suspend fun getEntriesByFormId(formId: String): List<Entry>
    suspend fun saveEntry(formId: String, entryId: String, fieldValues: Map<String, String>)
}
