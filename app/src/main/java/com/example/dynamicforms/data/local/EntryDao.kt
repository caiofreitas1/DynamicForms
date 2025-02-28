package com.example.dynamicforms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dynamicforms.data.model.EntryEntity

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryEntity)

    @Query("SELECT * FROM entries WHERE formId = :formId")
    suspend fun getEntriesByFormId(formId: String): List<EntryEntity>
}