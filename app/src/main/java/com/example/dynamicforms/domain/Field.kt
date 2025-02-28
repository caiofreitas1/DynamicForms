package com.example.dynamicforms.domain

data class Field(
    val fieldId: String,
    val parentFormId: String,
    val type: String,
    val label: String,
    val name: String,
    val required: Boolean,
    val orderIndex: Int,
    val options: List<Option> = emptyList()
)
