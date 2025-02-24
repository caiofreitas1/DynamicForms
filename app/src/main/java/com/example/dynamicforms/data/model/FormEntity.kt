package com.example.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forms")
data class FormEntity(
    @PrimaryKey val formId: String,
    val title: String,
)
