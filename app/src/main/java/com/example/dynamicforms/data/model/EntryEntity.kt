package com.example.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey val entryId: String,
    val formId: String,
    val fieldValuesJson: String,
    val timestamp: Long
)
