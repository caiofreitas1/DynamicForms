package com.example.dynamicforms.data.datasource

import com.example.dynamicforms.data.local.EntryDao
import com.example.dynamicforms.data.local.FieldDao
import com.example.dynamicforms.data.local.FormDao
import com.example.dynamicforms.data.local.OptionDao
import com.example.dynamicforms.data.local.SectionDao
import com.example.dynamicforms.data.model.EntryEntity
import com.example.dynamicforms.data.model.FieldEntity
import com.example.dynamicforms.data.model.FormEntity
import com.example.dynamicforms.data.model.OptionEntity
import com.example.dynamicforms.data.model.SectionEntity
import javax.inject.Inject

class FormLocalDataSourceImpl @Inject constructor(
    private val formDao: FormDao,
    private val sectionDao: SectionDao,
    private val fieldDao: FieldDao,
    private val optionDao: OptionDao,
    private val entryDao: EntryDao
) : FormLocalDataSource {

    override suspend fun insertForms(forms: List<FormEntity>) {
        formDao.insertForms(forms)
    }

    override suspend fun getAllForms(): List<FormEntity> {
        return formDao.getAllForms()
    }

    override suspend fun getFormById(formId: String): FormEntity? {
        return formDao.getFormById(formId)
    }

    override suspend fun insertSections(sections: List<SectionEntity>) {
        sectionDao.insertSections(sections)
    }

    override suspend fun getSectionsByFormId(formId: String): List<SectionEntity> {
        return sectionDao.getSectionsByFormId(formId)
    }

    override suspend fun insertFields(fields: List<FieldEntity>) {
        fieldDao.insertFields(fields)
    }

    override suspend fun getFieldsByFormId(formId: String): List<FieldEntity> {
        return fieldDao.getFieldsByFormId(formId)
    }

    override suspend fun insertOptions(options: List<OptionEntity>) {
        optionDao.insertOptions(options)
    }

    override suspend fun getOptionsByFieldId(fieldId: String): List<OptionEntity> {
        return optionDao.getOptionsByFieldId(fieldId)
    }

    override suspend fun insertEntry(entry: EntryEntity) {
        entryDao.insertEntry(entry)
    }

    override suspend fun getEntriesByFormId(formId: String): List<EntryEntity> {
        return entryDao.getEntriesByFormId(formId)
    }
}
