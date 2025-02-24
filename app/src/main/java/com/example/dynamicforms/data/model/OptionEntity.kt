package com.example.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "options")
data class OptionEntity(
    @PrimaryKey val optionId: String,
    val parentFieldId: String,
    val label: String,
    val value: String
)
