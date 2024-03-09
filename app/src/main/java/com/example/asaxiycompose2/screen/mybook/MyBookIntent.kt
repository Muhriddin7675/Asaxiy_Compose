package com.example.asaxiycompose2.screen.mybook

import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.screen.orderhestore.OrderIntent

interface MyBookIntent {
    data object LoadData:MyBookIntent
    data class ClickItem(val bookData: BookUIData): MyBookIntent

}