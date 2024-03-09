package com.example.asaxiycompose2.screen.orderhestore

import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.screen.allbook.AllBookIntent

interface OrderIntent {
    data class ClickItem(val bookData: BookUIData): OrderIntent
    data object loadOrderScreen:OrderIntent
}