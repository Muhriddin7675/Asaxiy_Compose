package com.example.asaxiycompose2.di

import com.example.asaxiybooks.domain.AppRepository
import com.example.asaxiycompose2.domain.implk.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun getRepo(impl: AppRepositoryImpl):AppRepository
}