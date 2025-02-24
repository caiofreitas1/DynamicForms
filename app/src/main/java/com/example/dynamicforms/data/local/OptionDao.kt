package com.example.dynamicforms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dynamicforms.data.model.OptionEntity

@Dao
interface OptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<OptionEntity>)

    @Query("SELECT * FROM options WHERE parentFieldId = :fieldId")
    suspend fun getOptionsByFieldId(fieldId: String): List<OptionEntity>
}
