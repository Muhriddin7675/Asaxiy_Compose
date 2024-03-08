package com.example.asaxiycompose2.screen.audiobook

import com.example.asaxiycompose2.data.model.AudioDataForAdapter
import com.example.asaxiycompose2.data.model.BookUIData

interface AudioIntent {
    data object GetAllCategoryList:AudioIntent
    data class ClickAllAudio(val list: AudioDataForAdapter): AudioIntent
    data class ClickItemAudio(val data: BookUIData): AudioIntent
}