package com.example.asaxiycompose2.di

import com.example.asaxiybooks.domain.LoginRepository
import com.example.asaxiybooks.domain.implk.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoginScreenModel {

    @Binds
    fun setUser(impl: LoginRepositoryImpl):LoginRepository

}