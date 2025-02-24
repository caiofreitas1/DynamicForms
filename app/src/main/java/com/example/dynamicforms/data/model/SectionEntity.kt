package com.example.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey val sectionId: String,
    val parentFormId: String,
    val title: String,
    val fromIndex: Int,
    val toIndex: Int,
    val index: Int
)
