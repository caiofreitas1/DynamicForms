package com.example.dynamicforms.di

import com.example.dynamicforms.data.datasource.FormLocalDataSource
import com.example.dynamicforms.data.repository.FormRepositoryImpl
import com.example.dynamicforms.domain.FormRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFormRepository(
        localDataSource: FormLocalDataSource
    ): FormRepository {
        return FormRepositoryImpl(localDataSource)
    }
}
