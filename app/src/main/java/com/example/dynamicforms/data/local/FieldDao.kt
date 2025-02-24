package com.example.dynamicforms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dynamicforms.data.model.FieldEntity

@Dao
interface FieldDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFields(fields: List<FieldEntity>)

    @Query("SELECT * FROM fields WHERE parentFormId = :formId")
    suspend fun getFieldsByFormId(formId: String): List<FieldEntity>
}
