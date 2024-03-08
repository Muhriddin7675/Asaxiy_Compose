package com.example.asaxiycompose2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyBooksUiData (
    val id:Long,
    val docId:String,
    val bookName:String,
    val bookAuthor:String,
    val description:String,
    val imagePath:String,
    val bookPath:String,
    val bookSize:String,
    val status: StatusEnum
):Parcelable