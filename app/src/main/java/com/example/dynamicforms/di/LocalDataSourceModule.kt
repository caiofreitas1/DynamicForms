package com.example.dynamicforms.di

import com.example.dynamicforms.data.datasource.FormLocalDataSource
import com.example.dynamicforms.data.datasource.FormLocalDataSourceImpl
import com.example.dynamicforms.data.local.EntryDao
import com.example.dynamicforms.data.local.FieldDao
import com.example.dynamicforms.data.local.FormDao
import com.example.dynamicforms.data.local.OptionDao
import com.example.dynamicforms.data.local.SectionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideFormLocalDataSource(
        formDao: FormDao,
        sectionDao: SectionDao,
        fieldDao: FieldDao,
        optionDao: OptionDao,
        entryDao: EntryDao
        ): FormLocalDataSource {
        return FormLocalDataSourceImpl(formDao, sectionDao, fieldDao, optionDao, entryDao)
    }
}
