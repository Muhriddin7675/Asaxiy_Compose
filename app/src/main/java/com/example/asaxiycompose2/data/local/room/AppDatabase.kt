package com.example.asaxiycompose2.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.asaxiycompose2.data.local.room.dao.BooksDao
import com.example.asaxiycompose2.data.local.room.entity.BookEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBooksDao(): BooksDao
}