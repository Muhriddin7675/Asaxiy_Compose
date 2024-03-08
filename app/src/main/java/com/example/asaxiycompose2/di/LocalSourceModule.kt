package com.example.asaxiycompose2.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.asaxiycompose2.data.local.room.AppDatabase
import com.example.asaxiycompose2.data.local.room.dao.BooksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalSourceModule {

    @[Provides Singleton]
    fun providePref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("Asaxiy", Context.MODE_PRIVATE)

    @[Provides Singleton]
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "MyBooks.db")
            .allowMainThreadQueries()
            .build()


    @[Provides Singleton]
    fun provideContactDao(database: AppDatabase) : BooksDao = database.getBooksDao()


}