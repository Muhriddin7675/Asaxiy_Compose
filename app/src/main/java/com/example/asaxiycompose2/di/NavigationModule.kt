package com.example.asaxiycompose2.di

import com.example.asaxiycompose2.navigation.AppNavigationHandler
import com.example.asaxiycompose2.navigation.AppNavigator
import com.example.asaxiycompose2.navigation.AppNavigatorDispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindNavigation(dispatcher: AppNavigatorDispatcher): AppNavigator

    @Binds
    fun bindHandler(dispatcher: AppNavigatorDispatcher): AppNavigationHandler
}