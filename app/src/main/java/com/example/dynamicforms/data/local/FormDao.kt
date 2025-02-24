package com.example.dynamicforms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dynamicforms.data.model.FormEntity

@Dao
interface FormDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForms(forms: List<FormEntity>)

    @Query("SELECT * FROM forms")
    suspend fun getAllForms(): List<FormEntity>

    @Query("SELECT * FROM forms WHERE formId = :formId LIMIT 1")
    suspend fun getFormById(formId: String): FormEntity
}