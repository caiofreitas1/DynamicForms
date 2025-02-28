package com.example.dynamicforms.domain

data class Entry(
    val entryId: String,
    val formId: String,
    val fieldValues: Map<String, String>,
    val timestamp: Long
)
