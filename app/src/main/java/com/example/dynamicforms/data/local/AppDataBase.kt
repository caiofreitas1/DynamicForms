package com.example.dynamicforms.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dynamicforms.data.model.FieldEntity
import com.example.dynamicforms.data.model.FormEntity
import com.example.dynamicforms.data.model.OptionEntity
import com.example.dynamicforms.data.model.SectionEntity

@Database(
    entities = [FormEntity::class, SectionEntity::class, FieldEntity::class, OptionEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun formDao(): FormDao
    abstract fun sectionDao(): SectionDao
    abstract fun fieldDao(): FieldDao
    abstract fun optionDao(): OptionDao
}
