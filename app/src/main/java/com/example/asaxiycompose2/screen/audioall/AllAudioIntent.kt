package com.example.asaxiycompose2.screen.audioall

import com.example.asaxiycompose2.data.model.BookUIData

interface AllAudioIntent {
    data class ClickItemAudio(val bookData:BookUIData):AllAudioIntent
}