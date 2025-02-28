package com.example.dynamicforms.ui.formentries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicforms.domain.Entry
import com.example.dynamicforms.domain.FormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormEntriesViewModel @Inject constructor(
    private val formRepository: FormRepository
) : ViewModel() {

    private val _entries = MutableStateFlow<List<Entry>>(emptyList())
    val entries: StateFlow<List<Entry>> = _entries

    fun loadEntries(formId: String) {
        viewModelScope.launch {
            val result = formRepository.getEntriesByFormId(formId)
            _entries.value = result
        }
    }
}
