package com.example.asaxiycompose2.screen.book_info

import com.example.asaxiycompose2.data.model.BookUIData

interface BookIntent {
    data object clickBack : BookIntent
    data class ClickDownlandButton(val data:BookUIData) : BookIntent

    data class HasBookFromLocal(val bookId: String) : BookIntent
    data class HasBookFromBuy(val bookId: String,val type: String) : BookIntent
    data class GetDownlandBook(val data: BookUIData):BookIntent
    data class AddBookBuy(val bookName: String, val type: String, val bookDocId: String):BookIntent

}