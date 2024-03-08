package com.example.asaxiycompose2.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asaxiycompose2.data.local.room.entity.BookEntity

@Dao
interface BooksDao {
    @Query("SELECT * FROM bookTable")
    fun getAllBooksFromLocal(): List<BookEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(data: BookEntity)

    @Query("Select id From bookTable where docId = :docId")
    fun isHasBook(docId:String):Boolean

    @Query("Select * From bookTable Where docId = :docId")
    fun getBooksByDocID(docId:String): BookEntity

    @Delete
    fun deleteBooks(data: BookEntity)

    @Query("DELETE FROM bookTable")
    fun nukeTable()
}