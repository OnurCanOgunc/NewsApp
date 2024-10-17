package com.decode.composenews.di

import com.decode.composenews.data.NewsRepositoryImpl
import com.decode.composenews.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}