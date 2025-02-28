package com.example.dynamicforms.domain

data class Section(
    val sectionId: String,
    val parentFormId: String,
    val title: String,
    val fromIndex: Int,
    val toIndex: Int,
    val index: Int
)
