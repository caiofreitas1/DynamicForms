package com.example.dynamicforms.di

import android.content.Context
import androidx.room.Room
import com.example.dynamicforms.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "dynamic_forms_db"
        ).build()
    }

    @Provides
    fun provideFormDao(db: AppDataBase) = db.formDao()

    @Provides
    fun provideSectionDao(db: AppDataBase) = db.sectionDao()

    @Provides
    fun provideFieldDao(db: AppDataBase) = db.fieldDao()

    @Provides
    fun provideOptionDao(db: AppDataBase) = db.optionDao()
}