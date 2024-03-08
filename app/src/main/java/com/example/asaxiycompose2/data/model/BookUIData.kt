package com.example.asaxiycompose2.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookUIData(
    val bookDocID: String,
    val bookName: String,
    val bookAuthor: String,
    val bookImage: String,
    var bookPath: String,
    val bookDescription: String,
    val bookSize: String,
    val category: String,
    val type: String,
    val categoryName:String
) : Parcelable

