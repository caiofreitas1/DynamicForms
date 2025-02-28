package com.example.dynamicforms.ui.formentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicforms.domain.Field
import com.example.dynamicforms.domain.Form
import com.example.dynamicforms.domain.FormRepository
import com.example.dynamicforms.domain.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FormEntryViewModel @Inject constructor(
    private val formRepository: FormRepository
) : ViewModel() {

    private val _form = MutableStateFlow<Form?>(null)
    val form: StateFlow<Form?> = _form

    private val _sections = MutableStateFlow<List<Section>>(emptyList())
    val sections: StateFlow<List<Section>> = _sections

    private val _fields = MutableStateFlow<List<Field>>(emptyList())
    val fields: StateFlow<List<Field>> = _fields

    private val _fieldValues = MutableStateFlow<Map<String, String>>(emptyMap())
    val fieldValues: StateFlow<Map<String, String>> = _fieldValues

    fun loadForm(formId: String) {
        viewModelScope.launch {
            val (form, sections, fields) = formRepository.getFormContents(formId) ?: return@launch
            _form.emit(form)
            _sections.emit(sections.sortedBy { it.index })
            _fields.emit(fields.sortedBy { it.orderIndex })
        }
    }

    fun updateFieldValue(fieldId: String, newValue: String) {
        val currentMap = _fieldValues.value.toMutableMap()
        currentMap[fieldId] = newValue
        _fieldValues.value = currentMap
    }

    suspend fun saveEntry() {
        val formId = _form.value?.formId ?: return
        val values = _fieldValues.value

        val entryId = UUID.randomUUID().toString()

        formRepository.saveEntry(formId = formId, entryId = entryId, fieldValues = values)
    }

}