package com.example.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class FieldEntity(
    @PrimaryKey val fieldId: String,
    val parentFormId: String,
    val type: String,
    val label: String,
    val name: String,
    val required: Boolean,
    val orderIndex: Int
)
