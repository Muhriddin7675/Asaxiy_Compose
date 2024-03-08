package com.example.asaxiycompose2.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookTable")
data class BookEntity (
    @PrimaryKey(autoGenerate = true) var id:Long,
    val docId:String,
    val bookName:String,
    val bookAuthor:String,
    val description:String,
    val imagePath:String,
    val bookPath:String,
    val bookSize:String,
    val category:String,
    val type:Int
)