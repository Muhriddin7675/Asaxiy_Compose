package com.example.asaxiycompose2.screen.allbook

import com.example.asaxiycompose2.data.model.BookUIData
import com.example.asaxiycompose2.screen.audioall.AllAudioIntent

interface AllBookIntent {
    data class ClickItem(val bookData:BookUIData):AllBookIntent
}