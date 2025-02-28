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
            val prefs = context.getSharedPreferences("forms_prefs", Context.MODE_PRIVATE)
            val formsLoaded = prefs.getBoolean("forms_loaded", false)

            if (!formsLoaded) {
                val firstFormDto = FormJsonParser.fromRaw(context, R.raw.all_fields)
                val secondFormDto = FormJsonParser.fromRaw(context, R.raw.form)
                val firstFormId = "form_all_fields"
                val secondFormId = "form_basic"

                firstFormDto?.let { formRepository.insertFormFromDto(formId = firstFormId, it) }
                secondFormDto?.let { formRepository.insertFormFromDto(formId = secondFormId, it) }
                prefs.edit().putBoolean("forms_loaded", true).apply()
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
}
