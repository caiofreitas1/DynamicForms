package com.example.dynamicforms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dynamicforms.data.model.SectionEntity

@Dao
interface SectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSections(sections: List<SectionEntity>)

    @Query("SELECT * FROM sections WHERE parentFormId = :formId")
    suspend fun getSectionsByFormId(formId: String): List<SectionEntity>
}