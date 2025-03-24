package com.example.dynamicforms.ui.formlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicforms.R
import com.example.dynamicforms.domain.Form
import com.example.dynamicforms.domain.FormRepository
import com.example.dynamicforms.utils.FormJsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormListViewModel @Inject constructor(
    private val formRepository: FormRepository
): ViewModel() {

    private val _forms = MutableStateFlow<List<Form>>(emptyList())
    val forms: StateFlow<List<Form>> = _forms

    fun loadAndInsertFormsIfNeeded(context: Context) {
        viewModelScope.launch {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val formsLoaded = prefs.getBoolean(KEY_FORMS_LOADED, false)

            if (!formsLoaded) {
                val firstFormDto = FormJsonParser.fromRaw(context, R.raw.all_fields)
                val secondFormDto = FormJsonParser.fromRaw(context, R.raw.form)

                firstFormDto?.let { formRepository.insertFormFromDto(formId = FORM_ALL_FIELDS_NAME, it) }
                secondFormDto?.let { formRepository.insertFormFromDto(formId = FORM_BASIC_NAME, it) }
                prefs.edit().putBoolean(KEY_FORMS_LOADED, true).apply()
            }
            loadForms()
        }
    }

    private fun loadForms() {
        viewModelScope.launch {
            val result = formRepository.getAllForms()
            _forms.emit(result)
        }
    }

    companion object {
        const val PREFS_NAME = "forms_prefs"
        const val KEY_FORMS_LOADED = "forms_loaded"

        const val FORM_ALL_FIELDS_NAME = "form_all_fields"
        const val FORM_BASIC_NAME = "form_basic"
    }
}
