package com.example.dynamicforms.data.datasource

import com.example.dynamicforms.data.model.EntryEntity
import com.example.dynamicforms.data.model.FieldEntity
import com.example.dynamicforms.data.model.FormEntity
import com.example.dynamicforms.data.model.OptionEntity
import com.example.dynamicforms.data.model.SectionEntity

interface FormLocalDataSource {
    suspend fun insertForms(forms: List<FormEntity>)
    suspend fun getAllForms(): List<FormEntity>
    suspend fun getFormById(formId: String): FormEntity?

    suspend fun insertSections(sections: List<SectionEntity>)
    suspend fun getSectionsByFormId(formId: String): List<SectionEntity>

    suspend fun insertFields(fields: List<FieldEntity>)
    suspend fun getFieldsByFormId(formId: String): List<FieldEntity>

    suspend fun insertOptions(options: List<OptionEntity>)
    suspend fun getOptionsByFieldId(fieldId: String): List<OptionEntity>

    suspend fun insertEntry(entry: EntryEntity)
    suspend fun getEntriesByFormId(formId: String): List<EntryEntity>
}
