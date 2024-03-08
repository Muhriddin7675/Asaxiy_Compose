package com.example.asaxiycompose2.utils

import android.content.Context
import android.widget.Toast
import com.example.asaxiycompose2.data.model.BookUIData
import com.google.firebase.firestore.QuerySnapshot
import timber.log.Timber

fun String.myLog(tag: String = "TTT") = Timber.tag(tag).d(this)

fun Context.toToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun String.toToast(context: Context) = Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

object Mapper {
    fun QuerySnapshot.toAudioDataList(): List<BookUIData> {
        val userList = mutableListOf<BookUIData>()
        for (document in documents) {
            val id = document.id
            val bookName = document.getString("bookName") ?: ""
            val bookAuthor = document.getString("bookAuthor") ?: ""
            val bookPath = document.getString("bookPath") ?: ""
            val imagePath = document.getString("imagePath") ?: ""
            val description = document.getString("description") ?: ""
            val category = document.getString("cotegory") ?: ""
            val type = document.getString("type") ?: ""
            val size = document.data!!.size
            val categoryName = document.getString("categoryName")?:""
            val bookUIData = BookUIData(
                id,
                bookName = bookName,
                bookAuthor = bookAuthor,
                bookImage = imagePath,
                bookPath = bookPath,
                bookDescription = description,
                bookSize = size.toString(),
                category = category,
                type = type,
                categoryName = categoryName
            )
            userList.add(bookUIData)
        }
        return userList
    }

    fun QuerySnapshot.toAudioDataListByAbdulbosit(): List<BookUIData> {

        val userList = mutableListOf<BookUIData>()
        for (document in documents) {
            val id = document.id
            val bookName = document.getString("audioName") ?: ""
            val bookAuthor = document.getString("audioAuthor") ?: ""
            val bookPath = document.getString("audioPath") ?: ""
            val imagePath = document.getString("audioImage") ?: ""
            val description = document.getString("audioDescription") ?: ""
            val category = document.getString("audioCategory") ?: ""
            val type = document.getString("type") ?: ""
            val size = document.data!!.size
            val categoryName = document.getString("category")?:""
            val bookUIData = BookUIData(
                id,
                bookName = bookName,
                bookAuthor = bookAuthor,
                bookImage = imagePath,
                bookPath = bookPath,
                bookDescription = description,
                bookSize = size.toString(),
                category = category,
                type = type,
                categoryName = categoryName
            )
            userList.add(bookUIData)
        }
        return userList
    }
}

